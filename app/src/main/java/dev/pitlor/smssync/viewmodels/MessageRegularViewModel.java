package dev.pitlor.smssync.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dev.pitlor.smssync.data.AppDatabase;
import dev.pitlor.smssync.data.daos.MessageDao;
import dev.pitlor.smssync.data.dto.MessageDTO;

public class MessageRegularViewModel extends AndroidViewModel {
    public LiveData<List<MessageDTO>> messages;

    public MessageRegularViewModel(@NonNull Application application) {
        super(application);
        MessageDao messageDao = AppDatabase.getInstance(application).messageDao();

        messages = messageDao.getAll();
    }
}
