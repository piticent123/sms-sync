package dev.pitlor.smssync.viewmodels;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.squareup.picasso.Picasso;

import java.io.File;

import dev.pitlor.smssync.repositories.AppRepository;
import dev.pitlor.smssync.tasks.SmsSync;

public class BaseViewModel extends AndroidViewModel {
    private Application application;
    protected AppRepository appRepository;

    @ViewModelInject
    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        appRepository = new AppRepository(application);
    }

    @BindingAdapter("visibleIf")
    public static void visibleIf(View view, Boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("imageUrl")
    public void setImage(ImageView imageView, File image) {
        Picasso.get().load(image).into(imageView);
    }

    public void sync(View view) {
        WorkRequest workRequest = new OneTimeWorkRequest
            .Builder(SmsSync.class)
            .setConstraints(SmsSync.getConstraints(application))
            .build();

        WorkManager.getInstance(application).enqueue(workRequest);
    }
}
