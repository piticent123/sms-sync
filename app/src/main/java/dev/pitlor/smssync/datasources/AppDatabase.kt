package dev.pitlor.smssync.datasources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import dev.pitlor.smssync.datasources.daos.ContactDao
import dev.pitlor.smssync.datasources.daos.MessageDao
import dev.pitlor.smssync.datasources.daos.SyncDao

@Database(entities = [Message::class, Sync::class, Contact::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun syncDao(): SyncDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val migrations = arrayOf<Migration>()

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, "sms-sync.db")
                    .addMigrations(*migrations)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}