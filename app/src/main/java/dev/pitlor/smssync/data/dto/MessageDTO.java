package dev.pitlor.smssync.data.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import dev.pitlor.smssync.data.entities.Contact;
import dev.pitlor.smssync.data.entities.Message;

public class MessageDTO {
    @Embedded
    public Message content;

    @Relation(parentColumn = "sender", entityColumn = "phoneNumber")
    public Contact sender;
}
