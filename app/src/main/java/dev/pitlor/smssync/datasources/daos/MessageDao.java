package dev.pitlor.smssync.datasources.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.time.OffsetDateTime;
import java.util.List;

import dev.pitlor.smssync.datasources.dto.MessageDTO;
import dev.pitlor.smssync.datasources.entities.Message;

@Dao
public interface MessageDao {
    @Transaction
    @Query("SELECT * FROM message")
    LiveData<List<MessageDTO>> getAll();

    @Transaction
    @Query("SELECT * FROM message WHERE id IN (:messageIds)")
    LiveData<List<MessageDTO>> loadAllByIds(int[] messageIds);

    @Query("SELECT SUM(id) FROM message GROUP BY id")
    LiveData<Integer> size();

    @Query("SELECT date FROM message ORDER BY date DESC LIMIT 1")
    LiveData<OffsetDateTime> timeOfLastSavedText();

    @Insert
    void insertAll(List<Message> messages);

    @Delete
    void delete(Message message);
}
