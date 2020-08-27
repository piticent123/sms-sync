package dev.pitlor.smssync.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;

public class SyncRegularViewModel extends BaseViewModel {
    @ViewModelInject
    public SyncRegularViewModel(@NonNull Application application) {
        super(application);
    }
}
