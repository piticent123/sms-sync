package dev.pitlor.sms.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dev.pitlor.sms.models.Mms;
import dev.pitlor.sms.models.Sms;

public class MessageRepository {
    private static final Uri CONVERSATIONS_URI = Uri.parse("content://mms-sms/conversations/");
    public static final int SMS = 1;
    public static final int MMS = 2;

    private Context context;

    @Inject
    public MessageRepository(Context context) {
        this.context = context;
    }

    public List<List<String>> getAllIdsAfter(OffsetDateTime minimumTime) {
        ContentResolver contentResolver = context.getContentResolver();
        final String[] projection = new String[]{"_id", "ct_t"};

        List<String> smsIds = new ArrayList<>();
        List<String> mmsIds = new ArrayList<>();
        Cursor cursor = contentResolver.query(CONVERSATIONS_URI, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("_id");
            int typeIndex = cursor.getColumnIndex("ct_t");

            do {
                String string = cursor.getString(typeIndex);
                if ("application/vnd.wap.multipart.related".equals(string)) {
                    mmsIds.add(cursor.getString(idColumnIndex));
                } else {
                    smsIds.add(cursor.getString(idColumnIndex));
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        List<List<String>> result = new ArrayList<>();
        result.add(smsIds);
        result.add(mmsIds);
        return result;
    }

    public Sms getSmsById(String id) {
//        Sms sms = Sms.Companion.Builder();
//        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor = contentResolver.query(
//            Telephony.Sms.CONTENT_URI,
//            new String[]{THREAD_ID, ADDRESS, BODY, DATE, SUBJECT},
//            Telephony.Sms._ID + " = ?",
//            new String[]{id},
//            null
//        );
//        if (cursor != null && cursor.moveToFirst()) {
//            sms
//                .address(cursor.getString(cursor.getColumnIndex(ADDRESS)))
//                .body(cursor.getString(cursor.getColumnIndex(BODY)))
//                .dateReceived(OffsetDateTime.ofInstant(
//                    Instant.ofEpochMilli(cursor.getLong(cursor.getColumnIndex(DATE))), ZoneId.systemDefault()
//                ))
//                .threadId(cursor.getLong(cursor.getColumnIndex(THREAD_ID)))
//                .subject(cursor.getString(cursor.getColumnIndex(SUBJECT)));
//            cursor.close();
//        }
//
//        return sms.build();
        return null;
    }

    public Mms getMmsById(String id) {
//        Mms.MmsBuilder mms = Mms.builder();
//        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor;
//
//        // Metadata
//        cursor = contentResolver.query(
//            Telephony.Mms.CONTENT_URI,
//            new String[]{Telephony.Mms.DATE, Telephony.Mms.SUBJECT, Telephony.Mms.THREAD_ID},
//            "_id = ?",
//            new String[]{id},
//            null
//        );
//        if (cursor != null && cursor.moveToFirst()) {
//            String subject = cursor.getString(cursor.getColumnIndex(Telephony.Mms.SUBJECT));
//            Instant dateRecieved = Instant.ofEpochSecond(cursor.getLong(cursor.getColumnIndex(Telephony.Mms.DATE)));
//
//            mms
//                .dateReceived(OffsetDateTime.ofInstant(dateRecieved, ZoneId.systemDefault()))
//                .subject(subject != null ? subject : "")
//                .threadId(cursor.getLong(cursor.getColumnIndex(Telephony.Mms.THREAD_ID)));
//
//            cursor.close();
//        }
//
//        // Text/Picture
//        cursor = contentResolver.query(
//            Telephony.Mms.Part.CONTENT_URI,
//            null,
//            Telephony.Mms.Part.MSG_ID + " = ?",
//            new String[]{id},
//            null
//        );
//        if (cursor != null && cursor.moveToFirst()) {
//            StringBuilder bodyBuilder = new StringBuilder();
//            do {
//                switch (cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part.CONTENT_TYPE))) {
//                    case "text/plain":
//                        String data = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part._DATA));
//                        String body;
//                        if (data != null) {
//                            body = getText(context, cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part._ID)));
//                        } else {
//                            body = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Part.TEXT));
//                        }
//                        bodyBuilder.append(body);
//                        mms.body(bodyBuilder.toString());
//                        break;
//                    case "image/jpeg":
//                    case "image/bmp":
//                    case "image/gif":
//                    case "image/jpg":
//                    case "image/png":
//                        File picture = getImage(context, cursor.getString(cursor.getColumnIndex(Telephony.Mms._ID)), id);
//                        mms.picture(picture);
//                        break;
//                }
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//
//        // Sender
//        cursor = contentResolver.query(
//            Uri.parse(MessageFormat.format("content://mms/{0}/addr", id)),
//            null,
//            Telephony.Mms.Addr.MSG_ID + " = ?",
//            new String[]{id},
//            null
//        );
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String number = cursor.getString(cursor.getColumnIndex(Telephony.Mms.Addr.ADDRESS));
//                if (number != null) {
//                    mms.address(number);
//                    break;
//                }
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//
//        return mms.build();
        return null;
    }

    private String getText(Context context, String id) {
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

    private File getImage(Context context, String partId, String messageId) {
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
