package dev.pitlor.sms

import java.io.File
import java.time.OffsetDateTime

data class Mms (
    var address: String = "",
    var dateReceived: OffsetDateTime = OffsetDateTime.MIN,
    var threadId: Long = -1,
    var picture: File? = null,
    var subject: String? = null,
    var body: String? = null
) {
    companion object {
        fun Builder(build: Mms.() -> Unit): Mms {
            val mms = Mms()
            mms.build()
            return mms
        }
    }
}

data class Sms (
    var address: String = "",
    var dateReceived: OffsetDateTime = OffsetDateTime.MIN,
    var threadId: Long = -1,
    var body: String = "",
    var subject: String? = null
) {
    companion object {
        fun Builder(build: Sms.() -> Unit): Sms {
            val sms = Sms()
            sms.build()
            return sms
        }
    }
}

data class Contact (
    val name: String,
    val phoneNumber: List<String>,
    val picture: File? = null
)

data class Message (
    val date: OffsetDateTime,
    val sender: String,
    val threadId: Long,
    val body: String? = null,
    val subject: String? = null,
    val image: File? = null,
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