package dev.pitlor.sms.models;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sms {
    String address;
    String body;
    OffsetDateTime dateReceived;
    long threadId;
    String subject;
}
