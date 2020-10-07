package dev.pitlor.smssync.tasks

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.preference.PreferenceManager
import androidx.work.*
import dev.pitlor.sms.Contacts
import dev.pitlor.sms.Message
import dev.pitlor.sms.Messages
import dev.pitlor.smssync.R
import dev.pitlor.smssync.datasources.AppRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class SmsSync @WorkerInject constructor(
    @param:Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val appRepository: AppRepository,
    private val messageRepository: Messages,
    private val contactRepository: Contacts
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            setProgress("Recording time of the sync")
            val sync = appRepository.addSync(id)

            setProgress("Checking permissions...")
            val results = listOf(
                context.checkSelfPermission(Manifest.permission.READ_SMS),
                context.checkSelfPermission(Manifest.permission.READ_CONTACTS)
            )
            if (results.stream().anyMatch { it == PackageManager.PERMISSION_DENIED }) {
                delay(500)
                setProgress("Checks failed. Please grant SMS/Contact read permissions and try again")
                appRepository.finishSync(sync)
                return@coroutineScope Result.failure()
            }
            setProgress("Checks passed!")

            setProgress("Finding last saved text")
            val timeOfLastSavedText = appRepository.getTimeOfLastSavedText()
            val forceFullSync = inputData.getBoolean(ForceFullSync, false)

            setProgress("Reading all newer texts from the phone")
            val messages: MutableList<Message>
            if (timeOfLastSavedText == null || forceFullSync) {
                messages = ArrayList()
                messageRepository.applyAll {
                    appRepository.addMessage(it)
                    messages.add(it)
                    setProgress("Processed message ${messages.size}", "${it.sender} - ${it.body}")
                }
            } else {
                messages = messageRepository.readAllAfter(timeOfLastSavedText).toMutableList()
                appRepository.addMessages(messages)
            }

            setProgress("Reading contacts")
            val newContacts = contactRepository.readAll(messages.map { it.sender }.distinct())

            setProgress("Adding new contacts and updating changed contacts")
            appRepository.addAndUpdateContacts(newContacts)

            setProgress("Finding preferred cloud provider")
            val cloudProviderEntries = context.resources.getStringArray(R.array.cloud_backup_provider_entries)
            val cloudProviderValues = context.resources.getStringArray(R.array.cloud_backup_provider_values)
            val cloudProvider = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString("cloudBackupProvider", "google_drive")
            val entryIndex = cloudProviderValues.indexOf(cloudProvider)
            if (entryIndex == -1) {
                setProgress("No preferred cloud provider found. Please set one and try again")
                appRepository.finishSync(sync)
                return@coroutineScope Result.failure()
            }
            val humanReadableProvider = cloudProviderEntries[entryIndex]

            setProgress("Uploading to $humanReadableProvider")
            // ...upload to *somewhere*

            delay(500)
            setProgress("Done!")
            appRepository.finishSync(sync)
            return@coroutineScope Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return@coroutineScope Result.failure()
        }
    }

    companion object {
        const val Progress = "PROGRESS"
        const val ForceFullSync = "FORCE_FULL_SYNC"

        fun getConstraints(context: Context): Constraints {
            val useData = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean("useMobileData", false)
            return Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .setRequiredNetworkType(if (useData) NetworkType.CONNECTED else NetworkType.UNMETERED)
                .build()
        }

        suspend fun CoroutineWorker.setProgress(progress: String, subtext: String? = null) {
            val channel = NotificationChannel(
                applicationContext.getString(R.string.notification_channel_id),
                applicationContext.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = applicationContext.getString(R.string.notification_channel_description)
            }
            getSystemService(applicationContext, NotificationManager::class.java)
                ?.createNotificationChannel(channel)

            val notification = NotificationCompat
                .Builder(applicationContext, applicationContext.getString(R.string.notification_channel_id))
                .setContentTitle(applicationContext.getString(R.string.notification_title))
                .setTicker(applicationContext.getString(R.string.notification_title))
                .setContentText(progress)
                .setSubText(subtext)
                .setSmallIcon(R.drawable.ic_sync_24dp)
                .setOngoing(true)
                .addAction(
                    android.R.drawable.ic_delete,
                    applicationContext.getString(R.string.cancel_sync),
                    WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
                )
                .build()

            setForeground(ForegroundInfo(R.string.notification_channel_id, notification))
        }
    }
}