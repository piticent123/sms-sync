package dev.pitlor.smssync.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;

import dev.pitlor.sms.Message;
import lombok.Setter;

public class ConversationsListItemViewModel extends BaseViewModel {
    @Setter public Message message;

    @ViewModelInject
    public ConversationsListItemViewModel(@NonNull Application application) {
        super(application);
    }
}
