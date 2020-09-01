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
    @get:Query("SELECT * FROM message")
    val all: LiveData<List<Message>>

    @Query("SELECT * FROM message WHERE id IN (:messageIds)")
    fun loadAllByIds(messageIds: List<Int>): LiveData<List<Message>>

    @get:Query("SELECT SUM(id) FROM message GROUP BY id")
    val size: LiveData<Int>

    @get:Query("SELECT date FROM message ORDER BY date DESC LIMIT 1")
    val timeOfLastSavedText: LiveData<OffsetDateTime>

    @Insert
    suspend fun insertAll(messages: List<Message>)

    @Delete
    suspend fun delete(message: Message)
}