package dev.pitlor.smssync;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.pitlor.smssync.daos.MessageDao;
import dev.pitlor.smssync.daos.SyncDao;
import dev.pitlor.smssync.entities.Converters;
import dev.pitlor.smssync.entities.Message;
import dev.pitlor.smssync.entities.Sync;

@Database(entities = {Message.class, Sync.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "sms-sync.db").build();
        }

        return instance;
    }

    public abstract MessageDao messageDao();
    public abstract SyncDao syncDao();
}
