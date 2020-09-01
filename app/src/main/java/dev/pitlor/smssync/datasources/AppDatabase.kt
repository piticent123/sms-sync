package dev.pitlor.smssync.datasources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dev.pitlor.smssync.activities.MainActivity
import dev.pitlor.smssync.datasources.daos.ContactDao
import dev.pitlor.smssync.datasources.daos.MessageDao
import dev.pitlor.smssync.datasources.daos.SyncDao

@Database(entities = [Message::class, Contact::class, Sync::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun syncDao(): SyncDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var databaseInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = databaseInstance
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, "sms-sync")
                    .build()
                databaseInstance = instance
                return instance
            }
        }
    }
}