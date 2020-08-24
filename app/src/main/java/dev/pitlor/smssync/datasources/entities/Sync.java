package dev.pitlor.smssync.datasources.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class Sync {
    @PrimaryKey public int id;
    @NonNull public OffsetDateTime date;
}
