package dev.pitlor.smssync.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.pitlor.smssync.db.daos.MessageDao;
import dev.pitlor.smssync.db.daos.SyncDao;
import dev.pitlor.smssync.db.entities.Converters;
import dev.pitlor.smssync.db.entities.Message;
import dev.pitlor.smssync.db.entities.Sync;

@Database(entities = {Message.class, Sync.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public abstract MessageDao messageDao();
    public abstract SyncDao syncDao();

    public static final Migration[] migrations = new Migration[] {

    };

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                .databaseBuilder(context, AppDatabase.class, "sms-sync.db")
                .addMigrations(migrations)
                .build();
        }

        return instance;
    }
}
