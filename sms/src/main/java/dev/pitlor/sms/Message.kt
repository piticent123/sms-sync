package dev.pitlor.sms

import dev.pitlor.sms.models.Mms
import dev.pitlor.sms.models.Sms
import java.io.File
import java.time.OffsetDateTime

data class Message (
    val date: OffsetDateTime,
    val sender: String,
    val threadId: Long,
    val body: String? = null,
    val subject: String? = null,
    val image: File? = null,
) {
    companion object {
        fun from(mms: Mms?): Message {
            if (mms == null) return Message(date = OffsetDateTime.MIN, sender = "", threadId = 0)

            return Message(
                sender=mms.address,
                date=mms.dateReceived,
                body=mms.body,
                subject=mms.subject,
                threadId=mms.threadId,
                image=mms.picture
            )
        }

        fun from(sms: Sms?): Message {
            if (sms == null) return Message(date = OffsetDateTime.MIN, sender = "", threadId = 0)

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