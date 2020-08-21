package dev.pitlor.smssync.data.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey public int id;
    public String name;
    public String phoneNumber;
    public Bitmap photo;
}
