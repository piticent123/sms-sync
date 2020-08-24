package dev.pitlor.smssync.datasources.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.OffsetDateTime;

import dev.pitlor.smssync.datasources.entities.Sync;

@Dao
public interface SyncDao {
    @Query("SELECT date FROM sync ORDER BY date LIMIT 1")
    LiveData<OffsetDateTime> getLastSync();

    @Insert
    void addSync(Sync date);
}
