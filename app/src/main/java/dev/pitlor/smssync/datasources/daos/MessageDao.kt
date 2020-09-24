package dev.pitlor.smssync.datasources.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.pitlor.smssync.datasources.Message
import dev.pitlor.smssync.datasources.MessageWithContact
import java.time.OffsetDateTime

@Dao
interface MessageDao: BaseDao<Message> {
    @Query("SELECT SUM(id) FROM message GROUP BY id")
    fun getSize(): LiveData<Int>

    @Query("SELECT date FROM message ORDER BY date DESC LIMIT 1")
    fun getTimeOfLastSavedText(): OffsetDateTime?

    @Transaction
    @Query("SELECT * FROM message WHERE id IN (SELECT max(id) FROM message GROUP BY threadId)")
    fun getAllThreads(): LiveData<List<MessageWithContact>>
}