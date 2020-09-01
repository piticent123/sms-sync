package dev.pitlor.smssync.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dev.pitlor.smssync.datasources.AppRepository
import dev.pitlor.smssync.tasks.SmsSync


open class BaseViewModel @ViewModelInject constructor(private val _application: Application) : AndroidViewModel(_application) {
    fun sync(@Suppress("UNUSED_PARAMETER") view: View) {
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(SmsSync::class.java)
            .setConstraints(SmsSync.getConstraints(_application))
            .build()
        WorkManager.getInstance(_application).enqueue(workRequest)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("visibleIf")
        fun visibleIf(view: View, visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun imageUrl(imageView: ImageView, image: Bitmap) {
//           Picasso.get().load(image).into(imageView)
            imageView.setImageBitmap(image)
        }
    }
}