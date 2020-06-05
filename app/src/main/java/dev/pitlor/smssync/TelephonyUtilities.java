package dev.pitlor.smssync;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

public class TelephonyUtilities {
    public static List<String> getAllSmsFromProvider(Context context, int limit) {
        List<String> lstSms = new ArrayList<String>();
        ContentResolver cr = context.getContentResolver();

        Cursor c = cr.query(Telephony.Sms.Inbox.CONTENT_URI, // Official CONTENT_URI from docs
                new String[]{Telephony.Sms.Inbox.BODY}, // Select body text
                null,
                null,
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER); // Default sort order

        int totalSMS = limit > 0 && limit <= c.getCount() ? limit : c.getCount();
        System.out.println(totalSMS);

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                String sms = c.getString(0);
                lstSms.add(sms);
                System.out.println(sms);
                c.moveToNext();
            }
        } else {
            throw new RuntimeException("You have no SMS in Inbox");
        }
        c.close();

        return lstSms;
    }
}
