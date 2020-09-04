package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.AppRepository

class MessageFragmentViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var messagesCount: Int? = null
    val isLoading get() = messagesCount == 0
}