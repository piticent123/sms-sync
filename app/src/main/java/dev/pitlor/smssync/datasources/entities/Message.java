package dev.pitlor.smssync.datasources.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Builder
@EqualsAndHashCode
public class Message {
    @PrimaryKey public int id;
    public String sender;
    public File photo;
    public String body;
    public OffsetDateTime date;
    public String subject;
    public long threadId;

    public static Message from(dev.pitlor.sms.Message message) {
        return Message
            .builder()
            .sender(message.getSender())
            .body(message.getBody())
            .photo(message.getImage())
            .date(message.getDate())
            .subject(message.getSubject())
            .threadId(message.getThreadId())
            .build();
    }
}
