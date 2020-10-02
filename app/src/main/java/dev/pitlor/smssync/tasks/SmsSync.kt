package dev.pitlor.smssync.tasks

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.preference.PreferenceManager
import androidx.work.*
import androidx.work.CoroutineWorker
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

        setProgress("Reading all newer texts from the phone")
        val messages: MutableList<Message>
        var numberOfMessagesProcessed = 0
        if (timeOfLastSavedText == null) {
            messages = ArrayList()
            messageRepository.applyAll {
                appRepository.addMessages(it)
                messages.addAll(it)
                setProgress("Processed messages $numberOfMessagesProcessed-${numberOfMessagesProcessed + it.size}")
                numberOfMessagesProcessed += it.size
            }
        } else {
            messages = messageRepository.readAllAfter(timeOfLastSavedText).toMutableList()
            appRepository.addMessages(messages)
        }

        setProgress("Reading contacts")
        val newContacts = contactRepository.readAll(messages.map { it.sender })

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
    }

    companion object {
        const val Progress = "PROGRESS"

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

        suspend fun CoroutineWorker.setProgress(progress: String) {
            setProgress(workDataOf(Progress to progress))
        }
    }
}