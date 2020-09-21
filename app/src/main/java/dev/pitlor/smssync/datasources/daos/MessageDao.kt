package dev.pitlor.smssync.datasources.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.pitlor.smssync.datasources.Message
import java.time.OffsetDateTime

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): LiveData<List<Message>>

    @Query("SELECT * FROM message WHERE id IN (:messageIds)")
    fun loadAllByIds(messageIds: List<Int>): LiveData<List<Message>>

    @Query("SELECT SUM(id) FROM message GROUP BY id")
    fun getSize(): LiveData<Int>

    @Query("SELECT date FROM message ORDER BY date DESC LIMIT 1")
    fun getTimeOfLastSavedText(): LiveData<OffsetDateTime>

    @Insert
    suspend fun insertAll(messages: List<Message>)

    @Delete
    suspend fun delete(message: Message)
}