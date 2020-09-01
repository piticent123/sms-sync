package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import java.time.OffsetDateTime

class SyncFragmentViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    val lastSync = appRepository.lastSync
    val isLoading = lastSync.value == null
}