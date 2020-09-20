package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import dev.pitlor.smssync.datasources.AppRepository

class MessageFragmentViewModel @ViewModelInject constructor(appRepository: AppRepository, application: Application) : BaseViewModel(application) {
    var messagesCount: LiveData<Int> = appRepository.messageCount
}