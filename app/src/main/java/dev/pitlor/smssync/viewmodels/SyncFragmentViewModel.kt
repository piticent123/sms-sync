package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import dev.pitlor.smssync.datasources.AppRepository
import dev.pitlor.smssync.datasources.Sync
import java.time.OffsetDateTime

class SyncFragmentViewModel @ViewModelInject constructor(appRepository: AppRepository, application: Application) : BaseViewModel(application) {
    var lastSync = appRepository.getLastSync()
}