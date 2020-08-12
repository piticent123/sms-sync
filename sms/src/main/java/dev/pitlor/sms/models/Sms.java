package dev.pitlor.sms.models;

import android.database.Cursor;
import android.provider.Telephony;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sms {
    public static final String[] COLUMNS = new String[] {
        Telephony.Sms.Inbox.ADDRESS,
        Telephony.Sms.Inbox.BODY,
        Telephony.Sms.Inbox.CREATOR,
        Telephony.Sms.Inbox.DATE,
        Telephony.Sms.Inbox.DATE_SENT,
        Telephony.Sms.Inbox.ERROR_CODE,
        Telephony.Sms.Inbox.LOCKED,
        Telephony.Sms.Inbox.PERSON,
        Telephony.Sms.Inbox.PROTOCOL,
        Telephony.Sms.Inbox.READ,
        Telephony.Sms.Inbox.REPLY_PATH_PRESENT,
        Telephony.Sms.Inbox.SEEN,
        Telephony.Sms.Inbox.SERVICE_CENTER,
        Telephony.Sms.Inbox.STATUS,
        Telephony.Sms.Inbox.SUBJECT,
        Telephony.Sms.Inbox.SUBSCRIPTION_ID,
        Telephony.Sms.Inbox.THREAD_ID,
        Telephony.Sms.Inbox.TYPE,
    };

    String address;
    String body;
    String creator;
    Date dateReceived;
    Date dateSent;
    int errorCode;
    boolean locked;
    String senderId;
    String protocol;
    boolean read;
    boolean replyPathPresent;
    boolean seen;
    String serviceCenter;
    MessageStatus status;
    String subject;
    long subscriptionId;
    long threadId;
    MessageType type;

    public static Sms from(String id) {
        return Sms.builder()
//            .address(c.getString(0))
//            .body(c.getString(1))
//            .creator(c.getString(2))
//            .dateReceived(new Date(c.getInt(3)))
//            .dateSent(new Date(c.getInt(4)))
//            .errorCode(c.getInt(5))
//            .locked(c.getInt(6) == 1)
//            .senderId(c.getString(7))
//            .protocol(c.getString(8))
//            .read(c.getInt(9) == 1)
//            .replyPathPresent(c.getInt(10) == 1)
//            .seen(c.getInt(11) == 1)
//            .serviceCenter(c.getString(12))
//            .status(MessageStatus.fromCode(c.getInt(13)))
//            .subject(c.getString(14))
//            .subscriptionId(c.getLong(15))
//            .threadId(c.getLong(16))
//            .type(MessageType.fromCode(c.getInt(17)))
            .build();
    }
}
