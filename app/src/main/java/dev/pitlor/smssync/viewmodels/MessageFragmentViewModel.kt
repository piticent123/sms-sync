package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.AppRepository

class MessageFragmentViewModel @ViewModelInject constructor(application: Application, appRepository: AppRepository) : BaseViewModel(application) {
    val messagesCount = appRepository.messageCount
    val isLoading = messagesCount.value == null
}