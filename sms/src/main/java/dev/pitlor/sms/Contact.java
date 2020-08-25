package dev.pitlor.sms;

import java.io.File;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {
    private String name;
    private List<String> phoneNumber;
    private File picture;
}
