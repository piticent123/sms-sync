package dev.pitlor.smssync.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dev.pitlor.smssync.datasources.entities.Message;
import dev.pitlor.smssync.datasources.entities.Contact;
import dev.pitlor.smssync.datasources.entities.Sync;
import lombok.Getter;

@Getter
public class AppRepository extends BaseRepository {
    private LiveData<OffsetDateTime> lastSync = syncDao.getLastSync();
    private LiveData<Integer> messageCount = messageDao.size();

    public AppRepository(Context context) {
        super(context);
    }

    public void addNewSyncRecord() {
        databaseWriteExecutor.execute(() -> syncDao.addSync(new Sync(OffsetDateTime.now())));
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
