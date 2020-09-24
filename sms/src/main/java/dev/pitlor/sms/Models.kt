package dev.pitlor.sms

import android.graphics.Bitmap
import java.time.OffsetDateTime

data class Mms (
    var address: String = "",
    var dateReceived: OffsetDateTime = OffsetDateTime.MIN,
    var threadId: Long = -1,
    var picture: Bitmap? = null,
    var subject: String? = null,
    var body: String? = null
)

data class Sms (
    var address: String = "",
    var dateReceived: OffsetDateTime = OffsetDateTime.MIN,
    var threadId: Long = -1,
    var body: String = "",
    var subject: String? = null
)

data class ContactLookupDTO(val id: Long, val key: String)

data class Contact (
    var name: String = "",
    var phoneNumber: List<String> = listOf(),
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
        fun from(mms: Mms): Message {
            return Message(
                sender=mms.address,
                date=mms.dateReceived,
                body=mms.body,
                subject=mms.subject,
                threadId=mms.threadId,
                image=mms.picture
            )
        }

        fun from(sms: Sms): Message {
            return Message(
                sender=sms.address,
                body=sms.body,
                date=sms.dateReceived,
                threadId=sms.threadId,
                subject=sms.subject
            )
        }
    }
}

data class MessagesDTO (val smsIds: List<String>, val mmsIds: List<String>)