package dev.pitlor.smssync.tasks

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.preference.PreferenceManager
import androidx.work.*
import dev.pitlor.sms.Contacts
import dev.pitlor.sms.Messages
import dev.pitlor.smssync.R
import dev.pitlor.smssync.repositories.AppRepository
import dev.pitlor.smssync.setProgress

class SmsSync @WorkerInject constructor(
    @param:Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val appRepository: AppRepository,
    private val messageRepository: Messages,
    private val contactRepository: Contacts
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        setProgress("Recording time of the sync")
//        appRepository.addSync()

        setProgress("Checking permissions...")
        val results = listOf(
            context.checkSelfPermission(Manifest.permission.READ_SMS),
            context.checkSelfPermission(Manifest.permission.READ_CONTACTS)
        )
        if (results.stream().anyMatch { it == PackageManager.PERMISSION_DENIED }) {
            setProgress("Checks failed. Please grant SMS/Contact read permissions and try again")
            return Result.failure()
        }
        setProgress("Checks passed!")

        setProgress("Finding last saved text")
        val timeOfLastSavedText = appRepository.timeOfLastSavedText.value

        setProgress("Reading all newer texts from the phone")
        val messages = messageRepository.readAllAfter(timeOfLastSavedText)
//        appRepository.addMessages(messages)

        setProgress("Reading contacts")
        val contacts = contactRepository.readAll()

        setProgress("Adding new contacts and updating changed contacts")
//        appRepository.addAndUpdateContacts(contacts)

        setProgress("Finding preferred cloud provider")
        val cloudProviderEntries = context.resources.getStringArray(R.array.cloud_backup_provider_entries)
        val cloudProviderValues = context.resources.getStringArray(R.array.cloud_backup_provider_values)
        val cloudProvider = PreferenceManager
            .getDefaultSharedPreferences(context)
            .getString("cloudProvider", "")
        val humanReadableProvider = cloudProviderEntries[cloudProviderValues.indexOf(cloudProvider)]
        if (cloudProvider == null || cloudProvider == "") {
            setProgress("No preferred cloud provider found. Please set one and try again")
            return Result.failure()
        }

        setProgress("Uploading to $humanReadableProvider")
        // ...upload to *somewhere*

        setProgress("Done!")
        return Result.success()
    }

    companion object {
        const val PROGRESS = "PROGRESS"
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
    }
}