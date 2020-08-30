package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject

class SyncFragmentViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var lastSync = appRepository.lastSync
    val isLoading = lastSync.value == null
}