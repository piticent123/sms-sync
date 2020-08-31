package dev.pitlor.smssync.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.pitlor.sms.Contact
import dev.pitlor.sms.Message
import javax.inject.Inject

class AppRepository @Inject constructor(@ApplicationContext context: Context) : BaseRepository(context) {
//    val allMessages = messageDao.all
//    val lastSync = syncDao.lastSync
//    val messageCount = messageDao.size
//    val timeOfLastSavedText = messageDao.timeOfLastSavedText

    suspend fun addSync() {
//        syncDao.addSync(Sync(OffsetDateTime.now()))
    }

    suspend fun addMessages(messages: List<Message>) {
//        val messagesAsEntities = messages.map { dev.pitlor.smssync.datasources.Message.from(it) }
//        messageDao.insertAll(messagesAsEntities)
    }

    suspend fun addAndUpdateContacts(contacts: List<Contact>) {
//        for (contact in contacts) {
//            val entity = contact.phoneNumber
//                .map { contactDao.getByNumber(it).value }
//                .find { it != null }
//
//            if (entity != null) {
//                entity.phoneNumbers = contact.phoneNumber
//                entity.name = contact.name
//                entity.photo = contact.picture
//                contactDao.update(entity)
//
//                return
//            }
//
//            contactDao.insert(dev.pitlor.smssync.datasources.Contact.from(contact))
//        }
    }
}