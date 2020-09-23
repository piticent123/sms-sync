package dev.pitlor.smssync.datasources

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.OffsetDateTime
import java.util.*

data class MessageWithContact(
    var content: Message,
    var sender: Contact?
)

@Entity
data class Contact(
    var name: String,
    var phoneNumbers: List<String>,
    var photo: Bitmap?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        fun from(contact: dev.pitlor.sms.Contact): Contact {
            return Contact(
                name = contact.name,
                phoneNumbers = contact.phoneNumber,
                photo = contact.picture
            )
        }
    }
}

@Entity
data class Message(
    var threadId: Long,
    var sender: String = "",
    var date: OffsetDateTime,
    var photo: Bitmap?,
    var body: String?,
    var subject: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        fun from(message: dev.pitlor.sms.Message): Message {
            return Message(
                sender = message.sender,
                body = message.body,
                photo = message.image,
                date = message.date,
                subject = message.subject,
                threadId = message.threadId,
            )
        }
    }
}

@Entity
data class Sync(
    var workRequestId: UUID,
    var startTime: OffsetDateTime,
    var endTime: OffsetDateTime? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}