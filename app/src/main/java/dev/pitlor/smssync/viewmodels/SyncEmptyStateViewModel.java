package dev.pitlor.smssync.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;

public class SyncEmptyStateViewModel extends BaseViewModel {
    @ViewModelInject
    public SyncEmptyStateViewModel(@NonNull Application application) {
        super(application);
    }

    public void importExistingData(View v) {
        // Import existing data from a cloud provider
    }
}
