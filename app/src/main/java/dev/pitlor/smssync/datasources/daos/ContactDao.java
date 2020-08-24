package dev.pitlor.smssync.datasources.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import dev.pitlor.smssync.datasources.entities.Contact;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact WHERE phoneNumber = :number")
    LiveData<Contact> getByNumber(String number);

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);
}
