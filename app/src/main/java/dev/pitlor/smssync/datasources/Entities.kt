package dev.pitlor.smssync.datasources

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.OffsetDateTime

data class MessageWithContact (
    @Embedded var content: Message,
    @Relation(parentColumn = "sender", entityColumn = "phoneNumbers") var sender: Contact
)

data class MessageDTO(
    var id: Int,
    var threadId: Long,
    var sender: String,
    var date: OffsetDateTime,
    var photo: Uri?,
    var body: String?,
    var subject: String?
) {
    companion object {
        fun from(context: Context, message: Message): MessageDTO {
            TODO()
        }
    }
}

@Entity
data class Contact (
    var name: String,
    var phoneNumbers: List<String>,
    var photo: Bitmap?
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0

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
data class Message (
    var threadId: Long,
    var sender: String = "",
    var date: OffsetDateTime,
    var photo: Bitmap?,
    var body: String?,
    var subject: String?
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0

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

@Entity data class Sync(
    var date: OffsetDateTime
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}