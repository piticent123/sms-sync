package dev.pitlor.smssync.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;

import dev.pitlor.smssync.datasources.AppDatabase;
import dev.pitlor.smssync.datasources.daos.SyncDao;

public class SyncFragmentViewModel extends AndroidViewModel {
    public LiveData<OffsetDateTime> lastSync;

    public SyncFragmentViewModel(@NonNull Application application) {
        super(application);
        SyncDao syncDao = AppDatabase.getInstance(application.getApplicationContext()).syncDao();

        lastSync = syncDao.getLastSync();
    }

    public boolean isLoading() {
        return lastSync == null;
    }
}
