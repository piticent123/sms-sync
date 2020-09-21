package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.AppRepository
import java.time.format.DateTimeFormatter

class SyncRegularViewModel @ViewModelInject constructor(appRepository: AppRepository, application: Application) : BaseViewModel(application) {
    val lastSync = appRepository.getLastSync()
    val lastSyncTime get() = if (lastSync.value == null) "" else DateTimeFormatter.RFC_1123_DATE_TIME.format(lastSync.value?.startTime)

    var loading = false
}