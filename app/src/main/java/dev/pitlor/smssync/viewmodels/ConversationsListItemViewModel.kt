package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.Message

class ConversationsListItemViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var message: Message? = null
}