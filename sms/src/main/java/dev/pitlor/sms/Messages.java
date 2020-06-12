package dev.pitlor.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

import dev.pitlor.sms.models.Mms;
import dev.pitlor.sms.models.Sms;

public class Messages {

    public static List<Message> ReadMostRecent(Context context, int limit) {
        List<Message> messages = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();

        Cursor smsCursor = contentResolver.query(
            Telephony.MmsSms.CONTENT_CONVERSATIONS_URI,
            Sms.COLUMNS,
            Telephony.MmsSms.TYPE_DISCRIMINATOR_COLUMN + " = sms",
            null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        );

        if (smsCursor == null) {
            throw new RuntimeException("The context resolver returned null");
        }

        int totalSMS = Math.min(limit > 0 ? limit : Integer.MAX_VALUE, smsCursor.getCount());
        if (smsCursor.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                messages.add(Message.from(Sms.from(smsCursor)));
                smsCursor.moveToNext();
            }
        }
        smsCursor.close();

        Cursor mmsCursor = contentResolver.query(
            Telephony.MmsSms.CONTENT_CONVERSATIONS_URI,
            Sms.COLUMNS,
            Telephony.MmsSms.TYPE_DISCRIMINATOR_COLUMN + " = sms",
            null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        );

        if (mmsCursor == null) {
            throw new RuntimeException("The context resolver returned null");
        }

        int totalMMS = Math.min(limit > 0 ? limit : Integer.MAX_VALUE, mmsCursor.getCount());
        if (mmsCursor.moveToFirst()) {
            for (int i = 0; i < totalMMS; i++) {
                messages.add(Message.from(Mms.from(mmsCursor)));
                mmsCursor.moveToNext();
            }
        }
        mmsCursor.close();

        return messages;
    }

    public static List<Message> ReadMostRecent(Context context) {
        return ReadMostRecent(context, 0);
    }
}
