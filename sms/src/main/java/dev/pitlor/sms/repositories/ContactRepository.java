package dev.pitlor.sms.repositories;

import android.content.Context;

import javax.inject.Inject;

public class ContactRepository {
    private Context context;

    @Inject
    public ContactRepository(Context context) {
        this.context = context;
    }
}
