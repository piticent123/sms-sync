package dev.pitlor.smssync.datasources

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Converters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        if (value == null || value == "") return null
        return formatter.parse(value) { OffsetDateTime.from(it) }
    }

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): String? {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @TypeConverter
    fun toBitmap(encodedString: String?): Bitmap? {
        val decodedBytes = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(string: String?): List<String>? {
        return string?.split(",")
    }

    @TypeConverter
    fun fromUuid(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUuid(string: String?): UUID? {
        return UUID.fromString(string)
    }
}