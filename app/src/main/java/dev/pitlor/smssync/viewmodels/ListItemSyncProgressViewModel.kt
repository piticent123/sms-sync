package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject

class ListItemSyncProgressViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var progressItem: String = ""
}