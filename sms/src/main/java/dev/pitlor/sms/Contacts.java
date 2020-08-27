package dev.pitlor.sms;

import java.util.List;

import javax.inject.Inject;

import dev.pitlor.sms.repositories.ContactRepository;

public class Contacts {
    private ContactRepository contactRepository;

    @Inject
    public Contacts(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> readAll(int limit) {
        return null;
    }
}
