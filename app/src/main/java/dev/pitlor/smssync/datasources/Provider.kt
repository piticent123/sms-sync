package dev.pitlor.smssync.datasources

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dev.pitlor.smssync.datasources.daos.ContactDao
import dev.pitlor.smssync.datasources.daos.MessageDao
import dev.pitlor.smssync.datasources.daos.SyncDao

@Module
@InstallIn(ApplicationComponent::class)
class Provider {
    @Volatile private var messageDaoInstance: MessageDao? = null
    @Volatile private var syncDaoInstance: SyncDao? = null
    @Volatile private var contactDaoInstance: ContactDao? = null

    @Provides
    fun getMessageDao(@ActivityContext context: Context): MessageDao {
        val tempInstance = messageDaoInstance
        if (tempInstance != null) {
            return tempInstance
        }

        synchronized(this) {
            val instance = AppDatabase.getInstance(context).messageDao()
            messageDaoInstance = instance
            return instance
        }
    }

    @Provides
    fun getSyncDao(@ActivityContext context: Context): SyncDao {
        val tempInstance = syncDaoInstance
        if (tempInstance != null) {
            return tempInstance
        }

        synchronized(this) {
            val instance = AppDatabase.getInstance(context).syncDao()
            syncDaoInstance = instance
            return instance
        }
    }

    @Provides
    fun getContactDao(@ActivityContext context: Context): ContactDao {
        val tempInstance = contactDaoInstance
        if (tempInstance != null) {
            return tempInstance
        }

        synchronized(this) {
            val instance = AppDatabase.getInstance(context).contactDao()
            contactDaoInstance = instance
            return instance
        }
    }

}