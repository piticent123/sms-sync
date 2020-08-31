package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject

class MessageFragmentViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    val messagesCount = 0 // appRepository.messageCount
    val isLoading = true // messagesCount.value == null
}