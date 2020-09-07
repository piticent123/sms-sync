package dev.pitlor.smssync.datasources.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.pitlor.smssync.datasources.Sync
import java.time.OffsetDateTime

@Dao
interface SyncDao {
    @get:Query("SELECT * FROM sync ORDER BY date DESC LIMIT 1")
    val lastSync: LiveData<Sync?>

    @Insert
    suspend fun addSync(date: Sync)
}