package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.AppRepository
import java.time.OffsetDateTime

class SyncFragmentViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var lastSync: OffsetDateTime? = null
    val isLoading get() = lastSync == null
}