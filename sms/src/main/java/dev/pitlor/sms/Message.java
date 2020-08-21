package dev.pitlor.sms;

import java.io.File;
import java.time.OffsetDateTime;

import dev.pitlor.sms.models.Mms;
import dev.pitlor.sms.models.Sms;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    OffsetDateTime date;
    String sender;
    String body;
    String subject;
    long threadId;
    File image;

    public static Message from(Mms mms) {
        return Message.builder()
            .sender(mms.getAddress())
            .date(mms.getDateReceived())
            .body(mms.getBody())
            .subject(mms.getSubject())
            .threadId(mms.getThreadId())
            .image(mms.getPicture())
            .build();
    }
    
    public static Message from(Sms sms) {
        return Message.builder()
            .sender(sms.getAddress())
            .body(sms.getBody())
            .date(sms.getDateReceived())
            .threadId(sms.getThreadId())
            .subject(sms.getSubject())
            .build();
    }
}
