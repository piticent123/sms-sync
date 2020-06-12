package dev.pitlor.sms.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageStatus {
    COMPLETE(0),
    FAILED(64),
    NONE(-1),
    PENDING(32);

    public final int code;

    public static MessageStatus fromCode(int c) {
        for (MessageStatus messageStatus : values()) {
            if (messageStatus.getCode() == c) {
                return messageStatus;
            }
        }
        return null;
    }
}
