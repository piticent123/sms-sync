package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.AppRepository
import java.time.OffsetDateTime

class SyncFragmentViewModel @ViewModelInject constructor(application: Application, appRepository: AppRepository) : BaseViewModel(application) {
    val lastSync = appRepository.lastSync
    val isLoading = lastSync.value == null
}