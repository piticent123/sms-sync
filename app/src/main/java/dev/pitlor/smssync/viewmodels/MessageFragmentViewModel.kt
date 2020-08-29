package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject

class MessageFragmentViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    val messagesCount = appRepository.messageCount
    val isLoading = messagesCount.value == null
}