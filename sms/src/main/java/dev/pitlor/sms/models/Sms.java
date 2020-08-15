package dev.pitlor.sms.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import lombok.Builder;
import lombok.Data;

import static android.provider.Telephony.TextBasedSmsColumns.*;

@Data
@Builder
public class Sms {
    String address;
    String body;
    OffsetDateTime dateReceived;
    long threadId;
    String subject;

    public static Sms from(Context context, String id) {
        SmsBuilder sms = Sms.builder();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            new String[]{THREAD_ID, ADDRESS, BODY, DATE, SUBJECT},
            Telephony.Sms._ID + " = ?",
            new String[]{id},
            null
        );
        if (cursor != null && cursor.moveToFirst()) {
            sms
                .address(cursor.getString(cursor.getColumnIndex(ADDRESS)))
                .body(cursor.getString(cursor.getColumnIndex(BODY)))
                .dateReceived(OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(cursor.getLong(cursor.getColumnIndex(DATE))), ZoneId.systemDefault()
                ))
                .threadId(cursor.getLong(cursor.getColumnIndex(THREAD_ID)))
                .subject(cursor.getString(cursor.getColumnIndex(SUBJECT)));
            cursor.close();
        }

        return sms.build();
    }
}
