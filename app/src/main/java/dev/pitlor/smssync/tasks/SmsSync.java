package dev.pitlor.smssync.tasks;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Arrays;
import java.util.List;

import dev.pitlor.sms.Contact;
import dev.pitlor.sms.Contacts;
import dev.pitlor.sms.Message;
import dev.pitlor.sms.Messages;
import dev.pitlor.smssync.datasources.AppDatabase;

public class SmsSync extends Worker {
    private Context context;

    public SmsSync(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    public Result doWork() {
        List<Integer> results = Arrays.asList(
            context.checkSelfPermission(Manifest.permission.READ_SMS),
            context.checkSelfPermission(Manifest.permission.READ_CONTACTS)
        );
        if (results.stream().anyMatch(r -> r == PackageManager.PERMISSION_DENIED)) {
            return Result.failure();
        }

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = cm
            .getNetworkCapabilities(cm.getActiveNetwork())
            .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        boolean isUsingData = cm.isActiveNetworkMetered();
        if (!isConnected || isUsingData) {
            return Result.retry();
        }

        AppDatabase appDatabase = AppDatabase.getInstance(context);

        Messages messageUtils = new Messages(context);
        List<Message> messages = messageUtils.readAll();

        Contacts contactUtils = new Contacts(context);
        List<Contact> contacts = contactUtils.readAll();

        return Result.success();
    }
}