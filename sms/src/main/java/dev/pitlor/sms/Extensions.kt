package dev.pitlor.sms

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import java.io.IOException
import java.io.InputStream
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

fun longToDate(date: Long): OffsetDateTime {
    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault())
}

fun Cursor.getString(column: String): String {
    return this.getString(this.getColumnIndex(column)) ?: ""
}

fun Cursor.getLong(column: String): Long {
    return this.getLong(this.getColumnIndex(column))
}

fun ContentResolver.queryLoop(
    uri: Uri,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    sortOrder: String? = null,
    useData: Cursor.() -> Unit
) {
    val cursor = query(uri, projection, selection, selectionArgs, sortOrder)
    if (cursor != null && cursor.moveToFirst()) {
        do {
            cursor.useData()
        } while (cursor.moveToNext())
        cursor.close()
    }
}

suspend fun ContentResolver.queryLoop(
    uri: Uri,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    sortOrder: String? = null,
    @Suppress("UNUSED_PARAMETER") isAsync: Boolean,
    useData: suspend Cursor.() -> Unit
) {
    val cursor = query(uri, projection, selection, selectionArgs, sortOrder)
    var i = 0
    if (cursor != null && cursor.moveToFirst()) {
        do {
            cursor.useData()
        } while (cursor.moveToNext() && i++ < 10)
        cursor.close()
    }
}

fun ContentResolver.queryOnce(
    uri: Uri,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    sortOrder: String? = null,
    useData: Cursor.() -> Unit
) {
    val cursor = query(uri, projection, selection, selectionArgs, sortOrder)
    if (cursor != null && cursor.moveToFirst()) {
        cursor.useData()
        cursor.close()
    }
}

fun ContentResolver.useInputStream(uri: Uri, useData: InputStream.() -> Unit) {
    var inputStream: InputStream? = null
    try {
        inputStream = openInputStream(uri)
        inputStream?.useData()
    } catch (e: IOException) {
        // Do nothing
    } finally {
        try {
            inputStream?.close()
        } catch (e: IOException) {
            // Do nothing
        }
    }
}

fun noop() {
    // do nothing
}