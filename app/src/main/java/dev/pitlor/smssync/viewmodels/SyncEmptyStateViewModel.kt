package dev.pitlor.smssync.viewmodels

import android.app.Application
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject

class SyncEmptyStateViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    fun importExistingData(@Suppress("UNUSED_PARAMETER") v: View?) {
        // Import existing data from a cloud provider
    }
}