package dev.pitlor.smssync.datasources.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import dev.pitlor.smssync.datasources.entities.Contact;
import dev.pitlor.smssync.datasources.entities.Message;

public class MessageDTO {
    @Embedded
    public Message content;

    @Relation(parentColumn = "sender", entityColumn = "phoneNumber")
    public Contact sender;
}
