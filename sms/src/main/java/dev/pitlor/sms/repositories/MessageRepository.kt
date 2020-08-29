package dev.pitlor.sms.repositories

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import android.provider.Telephony.TextBasedSmsColumns.*
import dev.pitlor.sms.*
import java.io.*
import java.nio.charset.StandardCharsets
import java.text.MessageFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class MessageRepository @Inject constructor(private val context: Context) {
    private val contentResolver = context.contentResolver!!

    fun getAllIdsAfter(minimumTime: OffsetDateTime?): MessagesDTO {
        val smsIds = ArrayList<String>()
        val mmsIds = ArrayList<String>()

        contentResolver.queryLoop(CONVERSATIONS_URI, projection = arrayOf("_id", "ct_t")) {
            if (getString("ct_t") == "application/vnd.wap.multipart.related") {
                mmsIds.add(getString("_id"))
            } else {
                smsIds.add(getString("_id"))
            }
        }

        return MessagesDTO(smsIds, mmsIds)
    }

    fun getSmsById(id: String): Sms {
        var address = ""
        var body = ""
        var subject = ""
        var dateReceived = OffsetDateTime.MIN
        var threadId: Long = 0
        contentResolver.queryOnce(
            Telephony.Sms.CONTENT_URI,
            arrayOf(THREAD_ID, ADDRESS, BODY, DATE, SUBJECT),
            Telephony.Sms._ID + " = ?",
            arrayOf(id),
            null
        ) {
            address = getString(ADDRESS)
            body = getString(BODY)
            dateReceived = OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(getLong(DATE)),
                ZoneId.systemDefault()
            )
            threadId = getLong(THREAD_ID)
            subject = getString(SUBJECT)
        }

        return Sms(address, dateReceived!!, threadId, body, subject)
    }

    fun getMmsById(id: String): Mms {
        // Metadata
        var dateReceived = OffsetDateTime.MIN
        var subject: String? = ""
        var threadId: Long = 0
        contentResolver.queryOnce(
            Telephony.Mms.CONTENT_URI,
            projection = arrayOf(Telephony.Mms.DATE, Telephony.Mms.SUBJECT, Telephony.Mms.THREAD_ID),
            selection = "_id = ?",
            selectionArgs = arrayOf(id)
        ) {
            subject = getString(Telephony.Mms.SUBJECT)
            dateReceived = OffsetDateTime.ofInstant(
                Instant.ofEpochSecond(getLong(Telephony.Mms.DATE)),
                ZoneId.systemDefault()
            )
            threadId = getLong(Telephony.Mms.THREAD_ID)
        }

        // Text/Picture
        val bodyBuilder = StringBuilder()
        var picture: File? = null
        contentResolver.queryLoop(
            Telephony.Mms.Part.CONTENT_URI,
            selection = Telephony.Mms.Part.MSG_ID + " = ?",
            selectionArgs = arrayOf(id),
        ) {
            when (getString(Telephony.Mms.Part.CONTENT_TYPE)) {
                "text/plain" -> bodyBuilder.append(getText(getString(Telephony.Mms.Part._ID)))
                "image/jpeg",
                "image/bmp",
                "image/gif",
                "image/jpg",
                "image/png" -> picture = getImage(getString(Telephony.Mms._ID), id)
            }
        }

        // Sender
        var address: String? = ""
        contentResolver.queryLoop(
            Uri.parse(MessageFormat.format("content://mms/{0}/addr", id)),
            selection = Telephony.Mms.Addr.MSG_ID + " = ?",
            selectionArgs = arrayOf(id),
        ) {
            when (val retrievedAddress = getString(Telephony.Mms.Addr.ADDRESS)) {
                "" -> noop()
                else -> address = retrievedAddress
            }
        }

        return Mms(address!!, dateReceived!!, threadId, picture, subject, bodyBuilder.toString())
    }

    private fun getText(id: String): String {
        val stringBuilder = StringBuilder()
        contentResolver.useInputStream(Uri.parse("content://mms/part/$id")) {
            val reader = BufferedReader(InputStreamReader(this, StandardCharsets.UTF_8))
            var line = reader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = reader.readLine()
            }
        }

        return stringBuilder.toString()
    }

    private fun getImage(partId: String, messageId: String): File? {
        val file = File(context.cacheDir, "${messageId}_${partId}.png")
        if (file.exists()) {
            return file
        }

        contentResolver.useInputStream(Uri.parse("content://mms/part/$partId")) {
            val buffer = ByteArray(available())
            read(buffer)
            val outputStream = FileOutputStream(file)
            outputStream.write(buffer)
        }

        return file
    }

    companion object {
        private val CONVERSATIONS_URI = Uri.parse("content://mms-sms/conversations/")
    }
}