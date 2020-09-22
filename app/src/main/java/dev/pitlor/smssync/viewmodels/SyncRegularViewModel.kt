package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.AppRepository
import java.time.format.DateTimeFormatter

class SyncRegularViewModel @ViewModelInject constructor(appRepository: AppRepository, application: Application) : BaseViewModel(application) {
    private val formatter = DateTimeFormatter.RFC_1123_DATE_TIME
    private val lastSync = appRepository.getLastSync()

    val lastSyncTime: String get() {
        val ref = lastSync.value
        return if (ref == null) "" else formatter.format(ref.startTime)
    }

    val loading: Boolean get() {
        val ref = lastSync.value
        return ref?.endTime == null
    }
}