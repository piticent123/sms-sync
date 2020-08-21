package dev.pitlor.smssync.db.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import dev.pitlor.smssync.db.entities.Contact;
import dev.pitlor.smssync.db.entities.Message;

public class MessageDTO {
    @Embedded
    public Message content;

    @Relation(parentColumn = "sender", entityColumn = "phoneNumber")
    public Contact sender;
}
