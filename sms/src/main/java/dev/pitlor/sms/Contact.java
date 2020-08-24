package dev.pitlor.sms;

import java.io.File;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {
    private String name;
    private String phoneNumber;
    private File picture;
}
