package dev.pitlor.smssync.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import dev.pitlor.permissions.Permissions;
import dev.pitlor.sms.Messages;
import dev.pitlor.smssync.adapters.MessageAdapter;
import dev.pitlor.smssync.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private Messages messages = new Messages(this);
    private Permissions permissions = new Permissions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding view = ActivityMainBinding.inflate(getLayoutInflater());
        permissions.assertPermission(Manifest.permission.READ_SMS);
        setContentView(view.getRoot());

        view.smsList.setHasFixedSize(true);
        view.smsList.setLayoutManager(new LinearLayoutManager(this));
        view.smsList.setAdapter(new MessageAdapter(messages.readAll()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.permissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}