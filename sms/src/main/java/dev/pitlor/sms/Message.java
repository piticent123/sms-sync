package dev.pitlor.sms;

import android.database.Cursor;

import java.util.Date;

import dev.pitlor.sms.models.MessageBox;
import dev.pitlor.sms.models.MessageStatus;
import dev.pitlor.sms.models.MessageType;
import dev.pitlor.sms.models.Mms;
import dev.pitlor.sms.models.Sms;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
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

    String address;
    String body;
    int errorCode;
    String senderId;
    String protocol;
    boolean replyPathPresent;
    String serviceCenter;
    MessageType type;
    
    public static Message from(Mms mms) {
        return Message.builder()
            .contentClass(mms.getContentClass())
            .contentLocation(mms.getContentLocation())
            .contentType(mms.getContentType())
            .creator(mms.getCreator())
            .dateReceived(mms.getDateReceived())
            .dateSent(mms.getDateSent())
            .deliveryReport(mms.getDeliveryReport())
            .deliveryTime(mms.getDeliveryTime())
            .expiryTime(mms.getExpiryTime())
            .locked(mms.isLocked())
            .messageBox(mms.getMessageBox())
            .messageClass(mms.getMessageClass())
            .messageId(mms.getMessageId())
            .messageSize(mms.getMessageSize())
            .messageType(mms.getMessageType())
            .mmsVersion(mms.getMmsVersion())
            .priority(mms.getPriority())
            .read(mms.isRead())
            .readReport(mms.isReadReport())
            .readStatus(mms.getReadStatus())
            .reportAllowed(mms.isReportAllowed())
            .responseStatus(mms.getResponseStatus())
            .responseText(mms.getResponseText())
            .retrieveStatus(mms.getRetrieveStatus())
            .retrieveText(mms.getRetrieveText())
            .retrieveTextCharset(mms.getRetrieveTextCharset())
            .seen(mms.isSeen())
            .status(mms.getStatus())
            .subject(mms.getSubject())
            .subjectCharset(mms.getSubjectCharset())
            .subscriptionId(mms.getSubscriptionId())
            .textOnly(mms.isTextOnly())
            .threadId(mms.getThreadId())
            .transactionId(mms.getTransactionId())
            .build();
    }
    
    public static Message from(Sms sms) {
        return Message.builder()
            .address(sms.getAddress())
            .body(sms.getBody())
            .creator(sms.getCreator())
            .dateReceived(sms.getDateReceived())
            .dateSent(sms.getDateSent())
            .errorCode(sms.getErrorCode())
            .locked(sms.isLocked())
            .senderId(sms.getSenderId())
            .protocol(sms.getProtocol())
            .read(sms.isRead())
            .replyPathPresent(sms.isReplyPathPresent())
            .seen(sms.isSeen())
            .serviceCenter(sms.getServiceCenter())
            .status(sms.getStatus())
            .subject(sms.getSubject())
            .subscriptionId(sms.getSubscriptionId())
            .threadId(sms.getThreadId())
            .type(sms.getType())
            .build();
    }
}
