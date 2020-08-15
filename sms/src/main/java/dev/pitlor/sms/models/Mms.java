package dev.pitlor.sms.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Telephony;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import lombok.Builder;
import lombok.Data;

import static android.provider.Telephony.Mms.CONTENT_URI;
import static android.provider.Telephony.Mms.SUBJECT;
import static android.provider.Telephony.Mms.THREAD_ID;
import static android.provider.Telephony.Mms._ID;
import static android.provider.Telephony.TextBasedSmsColumns.DATE;

@Data
@Builder
public class Mms {
    Bitmap picture;
    String address;
    OffsetDateTime dateReceived;
    String subject;
    long threadId;
    String body;

    public static Mms from(Context context, String id) {
        MmsBuilder mms = Mms.builder();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor;

        // Metadata
        cursor = contentResolver.query(
            CONTENT_URI,
            new String[] {DATE, SUBJECT, THREAD_ID},
            _ID + " = ?",
            new String[] {id},
            null
        );
        if (cursor != null && cursor.moveToFirst()) {
            mms
                .dateReceived(OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(cursor.getLong(cursor.getColumnIndex(DATE))), ZoneId.systemDefault()
                ))
                .subject(cursor.getString(cursor.getColumnIndex(SUBJECT)))
                .threadId(cursor.getLong(cursor.getColumnIndex(THREAD_ID)));

            cursor.close();
        }

        // Text/Picture
        cursor = contentResolver.query(
            Uri.parse("content://mms/part"),
            null,
            Telephony.Mms.MESSAGE_ID + " = ?",
            new String[] {id},
            null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                switch (cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part.CONTENT_TYPE))) {
                    case "text/plain":
                        String data = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part._DATA));
                        String body;
                        if (data != null) {
                            body = getText(context, cursor.getString(cursor.getColumnIndex(_ID)));
                        } else {
                            body = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part.TEXT));
                        }
                        mms.body((mms.body == null ? "" : mms.body) + body);
                        break;
                    case "image/jpeg":
                    case "image/bmp":
                    case "image/gif":
                    case "image/jpg":
                    case "image/png":
                        Bitmap picture = getImage(context, cursor.getString(cursor.getColumnIndex(_ID)));
                        mms.picture(picture);
                        break;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Sender
        cursor = contentResolver.query(
            Uri.parse(MessageFormat.format("content://mms/{0}/addr", id)),
            null,
            Telephony.Mms.MESSAGE_ID + " = ?",
            new String[] {id},
            null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String number = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Addr.ADDRESS));
                if (number != null) {
                    mms.address(number);
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return mms.build();
    }

    private static String getText(Context context, String id) {
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            inputStream = context.getContentResolver().openInputStream(Uri.parse("content://mms/part/" + id));
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            // Do nothing
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
        return stringBuilder.toString();
    }

    private static Bitmap getImage(Context context, String _id) {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = context.getContentResolver().openInputStream(Uri.parse("content://mms/part/" + _id));
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            // Do nothing
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
        return bitmap;
    }
}
