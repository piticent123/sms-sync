package dev.pitlor.sms.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.Telephony
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.pitlor.sms.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class MessageRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val contentResolver = context.contentResolver!!

    suspend fun applyAllMessages(saveMessages: suspend (List<MessageDTO>) -> Unit) {
        val messages = ArrayList<MessageDTO>()
        contentResolver.queryLoop(
            Telephony.MmsSms.CONTENT_CONVERSATIONS_URI,
            projection = arrayOf(Telephony.TextBasedSmsColumns.THREAD_ID),
            isAsync = true
        ) {
            val conversationId = getLong(Telephony.TextBasedSmsColumns.THREAD_ID)
            contentResolver.queryLoop(
                Uri.parse("content://mms-sms/conversations/$conversationId"),
                projection = arrayOf("_id", "ct_t"),
                isAsync = true
            ) {
                val messageDto = MessageDTO(getString("_id"), getString("ct_t") == MMS_TYPE)
                messages.add(messageDto)

                if (messages.size == 100) {
                    saveMessages(messages)
                    messages.clear()
                }
            }
        }
    }

    fun getAllIdsAfter(minimumTime: OffsetDateTime): MessagesDTO {
        val conversations = ArrayList<Long>()
        contentResolver.queryLoop(
            Telephony.MmsSms.CONTENT_CONVERSATIONS_URI,
            projection = arrayOf(Telephony.TextBasedSmsColumns.THREAD_ID),
        ) {
            conversations.add(getLong(Telephony.TextBasedSmsColumns.THREAD_ID))
        }

        val smsIds = ArrayList<String>()
        val mmsIds = ArrayList<String>()
        conversations.forEach { conversationId ->
            contentResolver.queryLoop(
                Uri.parse("content://mms-sms/conversations/$conversationId"),
                projection = arrayOf("_id", "ct_t"),
                selection = "${Telephony.TextBasedSmsColumns.DATE} > ?",
                selectionArgs = arrayOf(minimumTime.toString())
            ) {
                when (getString("ct_t")) {
                    "application/vnd.wap.multipart.related" -> mmsIds.add(getString("_id"))
                    else -> smsIds.add(getString("_id"))
                }
            }
        }

        return MessagesDTO(smsIds, mmsIds)
    }

    fun getSmsById(id: String): Sms {
        return Sms().apply {
            contentResolver.queryOnce(
                Telephony.Sms.CONTENT_URI,
                projection = arrayOf(
                    Telephony.TextBasedSmsColumns.THREAD_ID,
                    Telephony.TextBasedSmsColumns.ADDRESS,
                    Telephony.TextBasedSmsColumns.BODY,
                    Telephony.TextBasedSmsColumns.DATE,
                    Telephony.TextBasedSmsColumns.SUBJECT
                ),
                selection = "${Telephony.Sms._ID} = ?",
                selectionArgs = arrayOf(id)
            ) {
                address = getString(Telephony.TextBasedSmsColumns.ADDRESS)
                body = getString(Telephony.TextBasedSmsColumns.BODY)
                dateReceived = longToDate(getLong(Telephony.TextBasedSmsColumns.DATE))
                threadId = getLong(Telephony.TextBasedSmsColumns.THREAD_ID)
                subject = getString(Telephony.TextBasedSmsColumns.SUBJECT)
            }
        }
    }

    fun getMmsById(id: String): Mms {
        return Mms().apply {
            // Metadata
            contentResolver.queryOnce(
                Telephony.Mms.CONTENT_URI,
                projection = arrayOf(Telephony.Mms.DATE, Telephony.Mms.SUBJECT, Telephony.Mms.THREAD_ID),
                selection = "${Telephony.Mms._ID} = ?",
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
            contentResolver.queryLoop(
                Telephony.Mms.Part.CONTENT_URI,
                selection = "${Telephony.Mms.Part.MSG_ID} = ?",
                selectionArgs = arrayOf(id),
            ) {
                when (getString(Telephony.Mms.Part.CONTENT_TYPE)) {
                    "text/plain" -> body += getText(getString(Telephony.Mms.Part._ID))
                    "image/jpeg",
                    "image/bmp",
                    "image/gif",
                    "image/jpg",
                    "image/png" -> picture = getImage(getString(Telephony.Mms._ID))
                }
            }

            // Sender
            contentResolver.queryLoop(
                Uri.parse("content://mms/${id}/addr"),
                selection = "${Telephony.Mms.Addr.MSG_ID} = ?",
                selectionArgs = arrayOf(id),
            ) {
                when (val retrievedAddress = getString(Telephony.Mms.Addr.ADDRESS)) {
                    "" -> noop()
                    else -> address = retrievedAddress
                }
            }
        }
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

    private fun getImage(partId: String): Bitmap? {
        var bitmap: Bitmap? = null
        contentResolver.useInputStream(Uri.parse("content://mms/part/$partId")) {
            val buffer = ByteArray(available())
            read(buffer)
            bitmap = BitmapFactory.decodeByteArray(buffer, 0, available())
        }

        return bitmap
    }

    companion object {
        const val MMS_TYPE = "application/vnd.wap.multipart.related"
    }
}