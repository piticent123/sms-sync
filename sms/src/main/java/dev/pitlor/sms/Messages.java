package dev.pitlor.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.pitlor.sms.models.Mms;
import dev.pitlor.sms.models.Sms;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Messages {
    @NonNull private Context context;
    private Uri CONVERSATIONS_URI = Uri.parse("content://mms-sms/conversations/");

    public List<Message> readAllAfter(OffsetDateTime minimumTime) {
        List<Message> messages = new ArrayList<>();
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
