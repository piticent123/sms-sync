package dev.pitlor.smssync.datasources;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.pitlor.smssync.datasources.daos.ContactDao;
import dev.pitlor.smssync.datasources.daos.MessageDao;
import dev.pitlor.smssync.datasources.daos.SyncDao;
import dev.pitlor.smssync.datasources.entities.Contact;
import dev.pitlor.smssync.datasources.entities.Message;
import dev.pitlor.smssync.datasources.entities.Sync;

@Database(entities = {Message.class, Sync.class, Contact.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    private MessageDao messageDao;
    private SyncDao syncDao;
    private ContactDao contactDao;

    public abstract MessageDao messageDao();

    public abstract SyncDao syncDao();

    public abstract ContactDao contactDao();

    public static final Migration[] migrations = new Migration[]{

    };

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                .databaseBuilder(context, AppDatabase.class, "sms-sync.db")
                .addMigrations(migrations)
                .build();
            instance.init();
        }

        return instance;
    }

    private void init() {
        messageDao = messageDao();
        syncDao = syncDao();
        contactDao = contactDao();
    }

    public void addSync() {
        syncDao.addSync(new Sync(OffsetDateTime.now()));
    }

    public void addMessages(List<dev.pitlor.sms.Message> messages) {
        Message[] entities = messages.stream().map(Message::from).toArray(Message[]::new);
        messageDao.insertAll(entities);
    }
}
