package dev.pitlor.smssync.datasources

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

//data class MessageDTO (
//    @Embedded val content: Message,
//    @Relation(parentColumn = "sender", entityColumn = "phoneNumbers") var sender: Contact
//)

@Entity
data class Contact (
    var name: String?,
    var phoneNumbers: List<String>,
    var photo: Bitmap?
) {
    @PrimaryKey(autoGenerate = true) val id: Int = 0

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
    val threadId: Long,
    val sender: String,
    val date: OffsetDateTime?,
    val photo: Bitmap?,
    val body: String?,
    val subject: String?
) {
    @PrimaryKey(autoGenerate = true) val id: Int = 0

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
    val date: OffsetDateTime
) {
    @PrimaryKey(autoGenerate = true) val id: Int = 0
}