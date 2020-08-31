package dev.pitlor.smssync.repositories

import android.content.Context
import dev.pitlor.smssync.datasources.AppDatabase
//import dev.pitlor.smssync.datasources.daos.ContactDao
import dev.pitlor.smssync.datasources.daos.MessageDao
//import dev.pitlor.smssync.datasources.daos.SyncDao

open class BaseRepository(context: Context) {
//    var syncDao: SyncDao
    var messageDao: MessageDao
//    var contactDao: ContactDao

    init {
        val appDatabase = AppDatabase.getInstance(context)
//        syncDao = appDatabase.syncDao()
        messageDao = appDatabase.messageDao()
//        contactDao = appDatabase.contactDao()
    }
}