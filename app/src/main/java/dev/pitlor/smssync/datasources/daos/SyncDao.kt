package dev.pitlor.smssync.datasources.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.pitlor.smssync.datasources.Sync
import java.time.OffsetDateTime

@Dao
interface SyncDao: BaseDao<Sync> {
    @Query("SELECT * FROM sync ORDER BY startTime DESC LIMIT 1")
    fun getLastSync(): LiveData<Sync?>
}