package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.Message

class MessageRegularViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    val messages = arrayOf<Message>() // appRepository.allMessages
}