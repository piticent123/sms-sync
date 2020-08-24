package dev.pitlor.smssync.viewmodels;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import dev.pitlor.sms.Message;
import dev.pitlor.sms.Messages;
import dev.pitlor.smssync.datasources.AppDatabase;
import dev.pitlor.smssync.datasources.daos.ContactDao;
import dev.pitlor.smssync.datasources.daos.MessageDao;

public class BaseViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private MessageDao messageDao;
    private ContactDao contactDao;
    private Messages messageUtils;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);
        messageDao = appDatabase.messageDao();
        contactDao = appDatabase.contactDao();
        messageUtils = new Messages(application);
    }

    @BindingAdapter("visibleIf")
    public static void visibleIf(View view, Boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("imageUrl")
    public void setImage(ImageView imageView, File image) {
        Picasso.get().load(image).into(imageView);
    }

    public void sync() {
        appDatabase.addSync();

        // Get contacts

        List<Message> messages = messageUtils.readAll();
        appDatabase.addMessages(messages);
    }
}
