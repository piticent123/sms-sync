package dev.pitlor.sms.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

@Data
@Builder
public class Mms {
    File picture;
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
            Telephony.Mms.CONTENT_URI,
            new String[]{Telephony.Mms.DATE, Telephony.Mms.SUBJECT, Telephony.Mms.THREAD_ID},
            "_id = ?",
            new String[]{id},
            null
        );
        if (cursor != null && cursor.moveToFirst()) {
            String subject = cursor.getString(cursor.getColumnIndex(Telephony.Mms.SUBJECT));
            Instant dateRecieved = Instant.ofEpochSecond(cursor.getLong(cursor.getColumnIndex(Telephony.Mms.DATE)));

            mms
                .dateReceived(OffsetDateTime.ofInstant(dateRecieved, ZoneId.systemDefault()))
                .subject(subject != null ? subject : "")
                .threadId(cursor.getLong(cursor.getColumnIndex(Telephony.Mms.THREAD_ID)));

            cursor.close();
        }

        // Text/Picture
        cursor = contentResolver.query(
            Telephony.Mms.Part.CONTENT_URI,
            null,
            Telephony.Mms.Part.MSG_ID + " = ?",
            new String[]{id},
            null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                switch (cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part.CONTENT_TYPE))) {
                    case "text/plain":
                        String data = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part._DATA));
                        String body;
                        if (data != null) {
                            body = getText(context, cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part._ID)));
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
                        File picture = getImage(context, cursor.getString(cursor.getColumnIndex(Telephony.Mms._ID)), id);
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
            Telephony.Mms.Addr.MSG_ID + " = ?",
            new String[]{id},
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

    private static File getImage(Context context, String partId, String messageId) {
        File file = new File(context.getCacheDir(), messageId + "_" + partId + ".png");
        if (file.exists()) {
            return file;
        }

        ContentResolver contentResolver = context.getContentResolver();
        try (InputStream inputStream = contentResolver.openInputStream(Uri.parse("content://mms/part/" + partId))) {
            if (inputStream == null) {
                return null;
            }

            byte[] buffer = new byte[inputStream.available()];
            //noinspection ResultOfMethodCallIgnored
            inputStream.read(buffer);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
        } catch (Exception e) {
            // Do nothing
        }

        return file;
    }
}
