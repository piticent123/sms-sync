package dev.pitlor.smssync.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.pitlor.sms.Contact
import dev.pitlor.sms.Message
import dev.pitlor.smssync.datasources.Sync
import java.time.OffsetDateTime
import javax.inject.Inject

class AppRepository @Inject constructor(@ApplicationContext context: Context) : BaseRepository(context) {
    val allMessages = messageDao.all
    val lastSync = syncDao.lastSync
    val messageCount = messageDao.size
    val timeOfLastSavedText = messageDao.timeOfLastSavedText

    suspend fun addSync() {
        syncDao.addSync(Sync(date = OffsetDateTime.now()))
    }

    suspend fun addMessages(messages: List<Message>) {
        val messagesAsEntities = messages.map { dev.pitlor.smssync.datasources.Message.from(it) }
        messageDao.insertAll(messagesAsEntities)
    }

    suspend fun addAndUpdateContacts(contacts: List<Contact>) {
        val contactsAsEntities = contacts.map { dev.pitlor.smssync.datasources.Contact.from(it) }
        for (contact in contactsAsEntities) {
            var savedContact: dev.pitlor.smssync.datasources.Contact? = null
            for (phoneNumber in contact.phoneNumbers!!) {
                savedContact = contactDao.getByNumber(phoneNumber).value
                if (savedContact != null) {
                    break
                }
            }

            when (savedContact) {
                null -> contactDao.insert(contact)
                else -> {
                    contact.id = savedContact.id
                    contactDao.update(contact)
                }
            }
        }
    }
}