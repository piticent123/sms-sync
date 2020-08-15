package dev.pitlor.smssync.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.pitlor.smssync.AppRepository;
import dev.pitlor.smssync.entities.Message;

public class MessagesViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    private LiveData<Message> messages;

    public MessagesViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }
}
