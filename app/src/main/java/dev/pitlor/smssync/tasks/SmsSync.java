package dev.pitlor.smssync.tasks;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.hilt.Assisted;
import androidx.hilt.work.WorkerInject;
import androidx.preference.PreferenceManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Arrays;
import java.util.List;

import dev.pitlor.sms.Contacts;
import dev.pitlor.sms.Messages;
import dev.pitlor.smssync.R;
import dev.pitlor.smssync.repositories.AppRepository;

public class SmsSync extends Worker {
    private static final String PROGRESS = "PROGRESS";

    private Context context;
    private AppRepository appRepository;
    private Messages messageRepository;
    private Contacts contactRepository;

    @WorkerInject
    public SmsSync(
        @Assisted @NonNull Context context,
        @Assisted @NonNull WorkerParameters workerParams,
        AppRepository appRepository,
        Messages messageRepository,
        Contacts contactRepository
    ) {
        super(context, workerParams);
        this.context = context;
        this.appRepository = appRepository;
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
    }

    public static Constraints getConstraints(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean useData = preferences.getBoolean("useMobileData", false);

        return new Constraints
                .Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .setRequiredNetworkType(useData ? NetworkType.CONNECTED : NetworkType.UNMETERED)
                .build();
    }

    private void setProgress(String progress) {
        setProgressAsync(new Data.Builder().putString(PROGRESS, progress).build());
    }

    @NonNull
    public Result doWork() {
        setProgress("Recording time of the sync");
//        appRepository.addSync();

        setProgress("Checking permissions...");
        List<Integer> results = Arrays.asList(
                context.checkSelfPermission(Manifest.permission.READ_SMS),
                context.checkSelfPermission(Manifest.permission.READ_CONTACTS)
        );
        if (results.stream().anyMatch(r -> r == PackageManager.PERMISSION_DENIED)) {
            setProgress("Checks failed. Please grant SMS/Contact read permissions and try again");
            return Result.failure();
        }
        setProgress("Checks passed!");

        setProgress("Finding last saved text");
//        OffsetDateTime timeOfLastSavedText = appDatabase.messageDao.timeOfLastSavedText().getValue();
        setProgress("Reading all newer texts from the phone");
//        List<Message> messages = messageUtils.readAllAfter(timeOfLastSavedText);
//        appDatabase.addMessages(messages);

        setProgress("Reading contacts");
//        List<Contact> contacts = contactUtils.readAll();
        setProgress("Adding new contacts and updating changed contacts");
//        appDatabase.addAndUpdateContacts(contacts);

        setProgress("Finding preferred cloud provider");
        Resources resources = context.getResources();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        List<String> cloudProviderEntries = Arrays.asList(resources.getStringArray(R.array.cloud_backup_provider_entries));
        List<String> cloudProviderValues = Arrays.asList(resources.getStringArray(R.array.cloud_backup_provider_values));
        String cloudProvider = preferences.getString("cloudProvider", "");
        String humanReadableProvider = cloudProviderEntries.get(cloudProviderValues.indexOf(cloudProvider));

        if (cloudProvider == null || cloudProvider.equals("")) {
            setProgress("No preferred cloud provider found. Please set one and try again");
            return Result.failure();
        }

        setProgress("Uploading to " + humanReadableProvider);
        // ...upload to *somewhere*

        setProgress("Done!");
        return Result.success();
    }
}
