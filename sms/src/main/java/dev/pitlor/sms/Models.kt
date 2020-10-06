package dev.pitlor.sms

import android.graphics.Bitmap
import java.time.OffsetDateTime

sealed class Text

data class Mms (
    var address: String = "",
    var dateReceived: OffsetDateTime = OffsetDateTime.MIN,
    var threadId: Long = -1,
    var picture: Bitmap? = null,
    var subject: String? = null,
    var body: String? = null
): Text()

data class Sms (
    var address: String = "",
    var dateReceived: OffsetDateTime = OffsetDateTime.MIN,
    var threadId: Long = -1,
    var body: String = "",
    var subject: String? = null
): Text()

data class Contact (
    var name: String = "",
    var phoneNumber: String = "",
    var picture: Bitmap? = null
)

data class Message (
    val date: OffsetDateTime,
    val sender: String,
    val threadId: Long,
    val body: String? = null,
    val subject: String? = null,
    val image: Bitmap? = null,
) {
    companion object {
        fun from(text: Text): Message {
            return when(text) {
                is Mms -> Message(
                    sender=text.address,
                    date=text.dateReceived,
                    body=text.body,
                    subject=text.subject,
                    threadId=text.threadId,
                    image=text.picture
                )
                is Sms -> Message(
                    sender=text.address,
                    body=text.body,
                    date=text.dateReceived,
                    threadId=text.threadId,
                    subject=text.subject
                )
            }
        }
    }
}

data class MessagesDTO(val smsIds: List<String>, val mmsIds: List<String>)
data class MessageDTO(val id: String, val isMms: Boolean)