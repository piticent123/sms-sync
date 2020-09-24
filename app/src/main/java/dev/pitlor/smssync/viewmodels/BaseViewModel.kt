package dev.pitlor.smssync.viewmodels

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dev.pitlor.smssync.SmsSyncApplication
import dev.pitlor.smssync.tasks.SmsSync


open class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {
    fun sync(@Suppress("UNUSED_PARAMETER") view: View) {
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
            .build()
        WorkManager.getInstance(application).enqueue(workRequest)
    }

    companion object {
        var workLauncher: ActivityResultLauncher<Array<String>>? = null

        @JvmStatic
        @BindingAdapter("visibleIf")
        fun visibleIf(view: View, visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun imageUrl(imageView: ImageView, image: Bitmap?) {
            imageView.setImageBitmap(image)
        }
    }
}