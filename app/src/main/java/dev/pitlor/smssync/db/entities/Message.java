package dev.pitlor.smssync.db.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey public int id;
    public String sender;
    public Bitmap photo;
    public String body;
}
