package dev.pitlor.smssync.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

public class MessageFragmentViewModel extends BaseViewModel {
    public LiveData<Integer> messagesCount = appRepository.getMessageCount();

    @ViewModelInject
    public MessageFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean isLoading() {
        return messagesCount == null;
    }
}
