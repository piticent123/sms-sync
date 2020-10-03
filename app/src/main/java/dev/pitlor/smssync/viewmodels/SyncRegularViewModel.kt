package dev.pitlor.smssync.viewmodels

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.hilt.lifecycle.ViewModelInject
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import dev.pitlor.smssync.SmsSyncApplication
import dev.pitlor.smssync.datasources.AppRepository
import dev.pitlor.smssync.tasks.SmsSync
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class SyncRegularViewModel @ViewModelInject constructor(appRepository: AppRepository, application: Application) : BaseViewModel(application) {
    val lastSync = appRepository.getLastSync()

    fun fullSync(@Suppress("UNUSED_PARAMETER") view: View) {
        val application = getApplication<SmsSyncApplication>()
        val permissions = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS)
        val permissionResults = permissions.map(application::checkSelfPermission)
        val permissionsNeeded = permissions
            .filterIndexed { i, _ -> permissionResults[i] == PackageManager.PERMISSION_DENIED }
            .toTypedArray()

        if (permissionsNeeded.isNotEmpty()) {
            val launcher = workLauncher ?: return
            launcher.launch(permissionsNeeded)
            return
        }

        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(SmsSync::class.java)
            .setConstraints(SmsSync.getConstraints(application))
            .setInputData(workDataOf(SmsSync.ForceFullSync to true))
            .build()
        WorkManager.getInstance(application).enqueue(workRequest)
    }

    companion object {
        private val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

        @JvmStatic
        @BindingAdapter("visibleIfNull")
        fun visibleIfNull(view: View, time: OffsetDateTime?) {
            view.visibility = if (time == null) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("displayDateTime")
        fun displayDateTime(view: TextView, time: OffsetDateTime?) {
            if (time == null) return
            view.text = formatter.format(time)
        }
    }
}