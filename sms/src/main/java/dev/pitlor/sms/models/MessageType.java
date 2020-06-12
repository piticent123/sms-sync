package dev.pitlor.sms.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType {
    ALL(0),
    DRAFT(3),
    FAILED(5),
    INBOX(1),
    OUTBOX(4),
    QUEUED(6),
    SENT(2);

    public final int code;

    public static MessageType fromCode(int c) {
        for (MessageType messageType : values()) {
            if (messageType.getCode() == c) {
                return messageType;
            }
        }
        return null;
    }
}
