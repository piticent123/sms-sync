package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject

class SyncProgressListItemViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var progressItem: String = ""
}