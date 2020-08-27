package dev.pitlor.smssync.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;

import dev.pitlor.smssync.datasources.AppDatabase;
import dev.pitlor.smssync.datasources.daos.SyncDao;

public class SyncFragmentViewModel extends BaseViewModel {
    public LiveData<OffsetDateTime> lastSync = appRepository.getLastSync();

    @ViewModelInject
    public SyncFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean isLoading() {
        return lastSync == null;
    }
}
