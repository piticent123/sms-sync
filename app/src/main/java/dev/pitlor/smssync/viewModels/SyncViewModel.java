package dev.pitlor.smssync.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;

import dev.pitlor.smssync.AppRepository;

public class SyncViewModel extends AndroidViewModel {
    private AppRepository appRepository;

    private LiveData<OffsetDateTime> lastSync;

    public SyncViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);

        lastSync = appRepository.getLastSync();
    }

    public void addNewSyncRecord() {
        appRepository.addNewSyncRecord();
    }
}
