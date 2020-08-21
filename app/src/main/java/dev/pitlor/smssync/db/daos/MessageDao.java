package dev.pitlor.smssync.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import dev.pitlor.smssync.db.dto.MessageDTO;
import dev.pitlor.smssync.db.entities.Message;

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

    @Insert
    void insertAll(Message... messages);

    @Delete
    void delete(Message message);
}
