package dev.pitlor.smssync.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.pitlor.smssync.db.entities.Message;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message")
    LiveData<List<Message>> getAll();

    @Query("SELECT * FROM message WHERE id IN (:messageIds)")
    LiveData<List<Message>> loadAllByIds(int[] messageIds);

    @Insert
    void insertAll(Message... messages);

    @Delete
    void delete(Message message);
}
