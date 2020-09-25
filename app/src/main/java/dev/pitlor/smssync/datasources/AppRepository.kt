package dev.pitlor.smssync.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import dev.pitlor.sms.Contact
import dev.pitlor.sms.Message
import dev.pitlor.smssync.datasources.daos.ContactDao
import dev.pitlor.smssync.datasources.daos.MessageDao
import dev.pitlor.smssync.datasources.daos.SyncDao
import java.time.OffsetDateTime
import java.util.*
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val syncDao: SyncDao,
    private val contactDao: ContactDao
) {
    fun getLastSync() = syncDao.getLastSync()
    fun getMessageCount() = messageDao.getSize()
    fun getTimeOfLastSavedText() = messageDao.getTimeOfLastSavedText()
    fun getAllThreads() = messageDao.getAllThreads()

    suspend fun addSync(requestId: UUID): Sync {
        return Sync(requestId, OffsetDateTime.now()).apply {
            id = syncDao.insert(this)
        }
    }

    suspend fun finishSync(sync: Sync) {
        sync.endTime = OffsetDateTime.now()
        syncDao.update(sync)
    }

    suspend fun addMessages(messages: List<Message>) {
        val messagesAsEntities = messages.map { dev.pitlor.smssync.datasources.Message.from(it) }
        messageDao.insert(*messagesAsEntities.toTypedArray())
    }

    suspend fun addAndUpdateContacts(contacts: List<Contact>) {
        for (contact in contacts) {
            val entity = contactDao.getByNumber(contact.phoneNumber)
            if (entity != null) {
                entity.name = contact.name
                entity.photo = contact.picture
                contactDao.update(entity)
                continue
            }

            contactDao.insert(dev.pitlor.smssync.datasources.Contact.from(contact))
        }
    }
}

