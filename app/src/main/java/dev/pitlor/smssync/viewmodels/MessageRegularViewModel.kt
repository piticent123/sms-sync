package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import dev.pitlor.smssync.datasources.AppDatabase
import dev.pitlor.smssync.datasources.MessageDTO

class MessageRegularViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var messages: LiveData<List<MessageDTO?>?>?

    init {
        val messageDao = AppDatabase.getInstance(application).messageDao()
        messages = messageDao.all
    }
}