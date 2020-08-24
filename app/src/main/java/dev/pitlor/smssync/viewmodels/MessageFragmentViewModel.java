package dev.pitlor.smssync.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.pitlor.smssync.datasources.AppDatabase;
import dev.pitlor.smssync.datasources.daos.MessageDao;

public class MessageFragmentViewModel extends AndroidViewModel {
    public LiveData<Integer> messagesCount;

    public MessageFragmentViewModel(@NonNull Application application) {
        super(application);
        MessageDao messageDao = AppDatabase.getInstance(application).messageDao();

        messagesCount = messageDao.size();
    }

    public boolean isLoading() {
        return messagesCount == null;
    }
}
