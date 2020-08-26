package dev.pitlor.sms.models;

import java.io.File;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mms {
    File picture;
    String address;
    OffsetDateTime dateReceived;
    String subject;
    long threadId;
    String body;
}
