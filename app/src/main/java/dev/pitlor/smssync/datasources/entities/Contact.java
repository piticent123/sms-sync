package dev.pitlor.smssync.datasources.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Builder
@EqualsAndHashCode
public class Contact {
    @PrimaryKey public int id;
    public String name;
    public String phoneNumber;
    public Bitmap photo;
}
