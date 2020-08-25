package dev.pitlor.smssync.datasources.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Contact {
    @PrimaryKey
    @EqualsAndHashCode.Exclude
    public int id;

    public String name;
    public List<String> phoneNumbers;

    @EqualsAndHashCode.Exclude
    public Bitmap photo;

    public static Contact from(dev.pitlor.sms.Contact contact) {
        return Contact
                .builder()
                .name(contact.getName())
                .phoneNumbers(contact.getPhoneNumber())
//                .photo(contact.getPicture())
                .build();
    }
}
