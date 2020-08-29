package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import java.time.OffsetDateTime

class SyncFragmentViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var lastSync = appRepository.lastSync
    val isLoading = lastSync.value == null
}