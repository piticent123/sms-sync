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
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String? = null,
    var phoneNumbers: List<String>? = null,
    var photo: Bitmap? = null
) {
    companion object {
        fun from(contact: dev.pitlor.sms.Contact): Contact {
            return Contact(
                name = contact.name,
                phoneNumbers = contact.phoneNumber
                // photo = contact.getPicture()
            )
        }
    }
}

@Entity
data class Message (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var threadId: Long = 0,
    var sender: String = "",
    var date: OffsetDateTime? = null,
    var photo: Bitmap? = null,
    var body: String? = null,
    var subject: String? = null
) {
    companion object {
        fun from(message: dev.pitlor.sms.Message): Message {
            return Message(
                sender = message.sender,
                body = message.body,
                // photo = message.image,
                date = message.date,
                subject = message.subject,
                threadId = message.threadId,
            )
        }
    }
}

@Entity data class Sync(@PrimaryKey(autoGenerate = true) val id: Int = 0, var date: OffsetDateTime = OffsetDateTime.MIN)