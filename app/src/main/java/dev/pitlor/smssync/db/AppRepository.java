package dev.pitlor.smssync.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;

import dev.pitlor.smssync.db.daos.MessageDao;
import dev.pitlor.smssync.db.daos.SyncDao;
import dev.pitlor.smssync.db.entities.Sync;

public class AppRepository {
    private SyncDao syncDao;
    private MessageDao messageDao;

    public AppRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        syncDao = appDatabase.syncDao();
        messageDao = appDatabase.messageDao();
    }

    public void addNewSyncRecord() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            syncDao.addSync(new Sync(OffsetDateTime.now()));
        });
    }

    public LiveData<OffsetDateTime> getLastSync() {
        return syncDao.getLastSync();
    }
}
