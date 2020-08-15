package dev.pitlor.smssync;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;

import dev.pitlor.smssync.daos.MessageDao;
import dev.pitlor.smssync.daos.SyncDao;
import dev.pitlor.smssync.entities.Sync;

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
