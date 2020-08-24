package dev.pitlor.sms;

import android.content.Context;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Contacts {
    private Context context;

    public List<Contact> readAll(int limit) {
        return null;
    }

    public List<Contact> readAll() {
        return readAll(-1);
    }
}
