package dev.pitlor.sms.repositories

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import android.provider.Telephony.TextBasedSmsColumns.*
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.pitlor.sms.*
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class MessageRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val contentResolver = context.contentResolver!!

    fun getAllIdsAfter(minimumTime: OffsetDateTime?): MessagesDTO {
        val smsIds = ArrayList<String>()
        val mmsIds = ArrayList<String>()

        contentResolver.queryLoop(CONVERSATIONS_URI, projection = arrayOf("_id", "ct_t")) {
            when (getString("ct_t")) {
                "application/vnd.wap.multipart.related" -> mmsIds.add(getString("_id"))
                else -> smsIds.add(getString("_id"))
            }
        }

        return MessagesDTO(smsIds, mmsIds)
    }

    fun getSmsById(id: String): Sms {
        return Sms().apply {
            contentResolver.queryOnce(
                Telephony.Sms.CONTENT_URI,
                projection = arrayOf(THREAD_ID, ADDRESS, BODY, DATE, SUBJECT),
                selection = "${Telephony.Sms._ID} = ?",
                selectionArgs = arrayOf(id)
            ) {
                address = getString(ADDRESS)
                body = getString(BODY)
                dateReceived = longToDate(getLong(DATE))
                threadId = getLong(THREAD_ID)
                subject = getString(SUBJECT)
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
                    "image/png" -> picture = getImage(getString(Telephony.Mms._ID), id)
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