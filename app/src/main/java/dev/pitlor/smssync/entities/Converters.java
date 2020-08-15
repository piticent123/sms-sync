package dev.pitlor.smssync.entities;

import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @TypeConverter
    public OffsetDateTime toOffsetDateTime(String value) {
        return formatter.parse(value, OffsetDateTime::from);
    }

    @TypeConverter
    public String fromOffsetDateTime(OffsetDateTime date) {
        return date.format(formatter);
    }
}
