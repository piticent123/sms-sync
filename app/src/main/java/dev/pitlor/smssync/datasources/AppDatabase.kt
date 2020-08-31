package dev.pitlor.smssync.datasources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.pitlor.smssync.datasources.daos.MessageDao

@Database(entities = [Message::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
//    abstract fun syncDao(): SyncDao
//    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, "sms-sync")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}