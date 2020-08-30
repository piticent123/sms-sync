package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject

class MessageRegularViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    val messages = appRepository.allMessages
}