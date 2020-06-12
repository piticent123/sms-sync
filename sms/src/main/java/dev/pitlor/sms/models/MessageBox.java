package dev.pitlor.sms.models;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageBox {
    ALL(0),
    DRAFTS(3),
    FAILED(5),
    INBOX(1),
    OUTBOX(4),
    SENT(2);

    private final int code;

    public static MessageBox fromCode(int c) {
        for (MessageBox messageBox : values()) {
            if (messageBox.getCode() == c) {
                return messageBox;
            }
        }
        return null;
    }
}
