package dev.pitlor.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.pitlor.sms.models.Mms;
import dev.pitlor.sms.models.Sms;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Messages {
    private Context context;

    public List<Message> readAll(int limit) {
        List<Message> messages = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.parse("content://mms-sms/conversations/");
        final String[] projection = new String[]{"_id", "ct_t"};

        List<String> smsIds = new ArrayList<>();
        List<String> mmsIds = new ArrayList<>();
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("_id");
            int typeIndex = cursor.getColumnIndex("ct_t");
            int i = 0;

            do {
                String string = cursor.getString(typeIndex);
                if ("application/vnd.wap.multipart.related".equals(string)) {
                    mmsIds.add(cursor.getString(idColumnIndex));
                } else {
                    smsIds.add(cursor.getString(idColumnIndex));
                }
            } while (cursor.moveToNext() && (++i < limit));

            cursor.close();
        }

        messages.addAll(smsIds.stream()
            .map((id) -> Sms.from(context, id))
            .map(Message::from)
            .collect(Collectors.toList()));
        messages.addAll(mmsIds.stream()
            .map((id) -> Mms.from(context, id))
            .map(Message::from)
            .collect(Collectors.toList()));
        return messages;
    }
}
