package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.Message
import dev.pitlor.smssync.datasources.MessageWithContact

class ListItemConversationsViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var message: MessageWithContact? = null
}