package dev.pitlor.smssync.viewmodels

import android.app.Application
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.squareup.picasso.Picasso
import dev.pitlor.smssync.repositories.AppRepository
import dev.pitlor.smssync.tasks.SmsSync
import java.io.File

open class BaseViewModel @ViewModelInject constructor(private val _application: Application) : AndroidViewModel(_application) {
    val appRepository = AppRepository(_application)

    fun sync(@Suppress("UNUSED_PARAMETER") view: View) {
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(SmsSync::class.java)
            .setConstraints(SmsSync.getConstraints(_application))
            .build()
        WorkManager.getInstance(_application).enqueue(workRequest)
    }

    companion object {
        @BindingAdapter("visibleIf")
        fun visibleIf(view: View, visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.GONE
        }

        @BindingAdapter("imageUrl")
        fun imageUrl(imageView: ImageView, image: File) {
            Picasso.get().load(image).into(imageView)
        }
    }
}