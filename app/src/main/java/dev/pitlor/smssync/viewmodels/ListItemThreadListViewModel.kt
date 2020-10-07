package dev.pitlor.smssync.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import dev.pitlor.smssync.datasources.Message
import dev.pitlor.smssync.datasources.MessageWithContact

class ListItemThreadListViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    var message: MessageWithContact? = null
    val s get() = message?.sender?.name ?: message?.content?.sender
    val sender get() = "$s ($threadId)"
    val threadId get() = message?.content?.threadId.toString()
}