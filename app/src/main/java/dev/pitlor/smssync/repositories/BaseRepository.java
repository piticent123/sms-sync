package dev.pitlor.smssync.repositories;

import android.content.Context;

import java.util.concurrent.ExecutorService;

import dev.pitlor.smssync.datasources.AppDatabase;
import dev.pitlor.smssync.datasources.daos.ContactDao;
import dev.pitlor.smssync.datasources.daos.MessageDao;
import dev.pitlor.smssync.datasources.daos.SyncDao;

public class BaseRepository {
    SyncDao syncDao;
    MessageDao messageDao;
    ContactDao contactDao;
    static final ExecutorService databaseWriteExecutor = AppDatabase.databaseWriteExecutor;

    public BaseRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        syncDao = appDatabase.syncDao();
        messageDao = appDatabase.messageDao();
        contactDao = appDatabase.contactDao();
    }
}
