package dev.pitlor.smssync.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.AppRepository
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class SyncRegularViewModel @ViewModelInject constructor(appRepository: AppRepository, application: Application) : BaseViewModel(application) {
    val lastSync = appRepository.getLastSync()

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