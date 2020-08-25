package dev.pitlor.smssync.datasources;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import dev.pitlor.smssync.Utils;
import dev.pitlor.smssync.datasources.daos.ContactDao;
import dev.pitlor.smssync.datasources.daos.MessageDao;
import dev.pitlor.smssync.datasources.daos.SyncDao;
import dev.pitlor.smssync.datasources.entities.Contact;
import dev.pitlor.smssync.datasources.entities.Message;
import dev.pitlor.smssync.datasources.entities.Sync;

import static dev.pitlor.smssync.Utils.nonNull;

@Database(entities = {Message.class, Sync.class, Contact.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public MessageDao messageDao;
    public SyncDao syncDao;
    public ContactDao contactDao;

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
        List<Message> messagesAsEntities = messages
                .stream()
                .map(Message::from)
                .collect(Collectors.toList());
        messageDao.insertAll(messagesAsEntities);
    }

    public void addAndUpdateContacts(List<dev.pitlor.sms.Contact> contacts) {
        List<Contact> contactsAsEntities = contacts
                .stream()
                .map(Contact::from)
                .collect(Collectors.toList());

        for (Contact contact : contactsAsEntities) {
            Contact savedContact = null;
            for (String phoneNumber : contact.phoneNumbers) {
                savedContact = contactDao.getByNumber(phoneNumber).getValue();

                if (savedContact != null) {
                    break;
                }
            }

            if (savedContact != null) {
                contact.id = savedContact.id;
                contactDao.update(contact);
            } else {
                contactDao.insert(contact);
            }
        }
    }
}
