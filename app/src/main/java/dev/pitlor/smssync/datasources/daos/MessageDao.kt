package dev.pitlor.smssync.datasources.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.pitlor.smssync.datasources.Message
import dev.pitlor.smssync.datasources.MessageDTO
import java.time.OffsetDateTime

@Dao
interface MessageDao {
    @get:Query("SELECT * FROM message")
    @get:Transaction
    val all: LiveData<List<MessageDTO?>?>?

    @Transaction
    @Query("SELECT * FROM message WHERE id IN (:messageIds)")
    fun loadAllByIds(messageIds: IntArray?): LiveData<List<MessageDTO?>?>?

    @Query("SELECT SUM(id) FROM message GROUP BY id")
    fun size(): LiveData<Int?>?

    @Query("SELECT date FROM message ORDER BY date DESC LIMIT 1")
    fun timeOfLastSavedText(): LiveData<OffsetDateTime?>?

    @Insert
    fun insertAll(messages: List<Message?>?)

    @Delete
    fun delete(message: Message?)
}