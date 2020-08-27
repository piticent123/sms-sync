package dev.pitlor.smssync.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dev.pitlor.smssync.datasources.AppDatabase;
import dev.pitlor.smssync.datasources.daos.MessageDao;
import dev.pitlor.smssync.datasources.dto.MessageDTO;

public class MessageRegularViewModel extends BaseViewModel {
    public LiveData<List<MessageDTO>> messages;

    @ViewModelInject
    public MessageRegularViewModel(@NonNull Application application) {
        super(application);
        MessageDao messageDao = AppDatabase.getInstance(application).messageDao();

        messages = messageDao.getAll();
    }
}
