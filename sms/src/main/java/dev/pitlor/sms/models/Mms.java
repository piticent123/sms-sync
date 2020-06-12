package dev.pitlor.sms.models;

import android.database.Cursor;
import android.provider.Telephony;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mms {
    public static final String[] COLUMNS = new String[] {
        Telephony.Mms.CONTENT_CLASS,
        Telephony.Mms.CONTENT_LOCATION,
        Telephony.Mms.CONTENT_TYPE,
        Telephony.Mms.CREATOR,
        Telephony.Mms.DATE,
        Telephony.Mms.DATE_SENT,
        Telephony.Mms.DELIVERY_REPORT,
        Telephony.Mms.DELIVERY_TIME,
        Telephony.Mms.EXPIRY,
        Telephony.Mms.LOCKED,
        Telephony.Mms.MESSAGE_BOX,
        Telephony.Mms.MESSAGE_CLASS,
        Telephony.Mms.MESSAGE_ID,
        Telephony.Mms.MESSAGE_SIZE,
        Telephony.Mms.MESSAGE_TYPE,
        Telephony.Mms.MMS_VERSION,
        Telephony.Mms.PRIORITY,
        Telephony.Mms.READ,
        Telephony.Mms.READ_REPORT,
        Telephony.Mms.READ_STATUS,
        Telephony.Mms.REPORT_ALLOWED,
        Telephony.Mms.RESPONSE_STATUS,
        Telephony.Mms.RESPONSE_TEXT,
        Telephony.Mms.RETRIEVE_STATUS,
        Telephony.Mms.RETRIEVE_TEXT,
        Telephony.Mms.RETRIEVE_TEXT_CHARSET,
        Telephony.Mms.SEEN,
        Telephony.Mms.STATUS,
        Telephony.Mms.SUBJECT,
        Telephony.Mms.SUBJECT_CHARSET,
        Telephony.Mms.SUBSCRIPTION_ID,
        Telephony.Mms.TEXT_ONLY,
        Telephony.Mms.THREAD_ID,
        Telephony.Mms.TRANSACTION_ID,
    };

    String contentClass;
    String contentLocation;
    String contentType;
    String creator;
    Date dateReceived;
    Date dateSent;
    int deliveryReport;
    Date deliveryTime;
    Date expiryTime;
    boolean locked;
    MessageBox messageBox;
    String messageClass;
    String messageId;
    int messageSize;
    MessageType messageType;
    int mmsVersion;
    int priority;
    boolean read;
    boolean readReport;
    int readStatus;
    boolean reportAllowed;
    int responseStatus;
    String responseText;
    int retrieveStatus;
    String retrieveText;
    int retrieveTextCharset;
    boolean seen;
    MessageStatus status;
    String subject;
    int subjectCharset;
    long subscriptionId;
    boolean textOnly;
    long threadId;
    String transactionId;

    public static Mms from(Cursor c) {
        return Mms.builder()
            .contentClass(c.getString(0))
            .contentLocation(c.getString(1))
            .contentType(c.getString(2))
            .creator(c.getString(3))
            .dateReceived(new Date(c.getLong(4)))
            .dateSent(new Date(c.getLong(5)))
            .deliveryReport(c.getInt(6))
            .deliveryTime(new Date(c.getLong(7)))
            .expiryTime(new Date(c.getLong(8)))
            .locked(c.getInt(9) == 1)
            .messageBox(MessageBox.fromCode(c.getInt(10)))
            .messageClass(c.getString(11))
            .messageId(c.getString(12))
            .messageSize(c.getInt(13))
            .messageType(MessageType.fromCode(c.getInt(14)))
            .mmsVersion(c.getInt(15))
            .priority(c.getInt(16))
            .read(c.getInt(17) == 1)
            .readReport(c.getInt(18) == 1)
            .readStatus(c.getInt(19))
            .reportAllowed(c.getInt(20) == 1)
            .responseStatus(c.getInt(21))
            .responseText(c.getString(22))
            .retrieveStatus(c.getInt(23))
            .retrieveText(c.getString(24))
            .retrieveTextCharset(c.getInt(25))
            .seen(c.getInt(26) == 1)
            .status(MessageStatus.fromCode(c.getInt(27)))
            .subject(c.getString(28))
            .subjectCharset(c.getInt(29))
            .subscriptionId(c.getLong(30))
            .textOnly(c.getInt(31) == 1)
            .threadId(c.getLong(32))
            .transactionId(c.getString(33))
            .build();
    }
}
