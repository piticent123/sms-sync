package dev.pitlor.smssync.activities.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.stream.Collectors;

import dev.pitlor.sms.Message;
import dev.pitlor.sms.Messages;
import dev.pitlor.smssync.R;
import dev.pitlor.smssync.activities.MainActivity;
import dev.pitlor.smssync.adapters.MessagesAdapter;
import dev.pitlor.smssync.databinding.FragmentMessagesBinding;
import dev.pitlor.smssync.databinding.FragmentMessagesNoMessagesBinding;
import dev.pitlor.smssync.databinding.FragmentMessagesRegularBinding;
import dev.pitlor.smssync.databinding.LayoutLoadingBinding;

public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new Thread(() -> {
            int readSmsResult = MainActivity.getInstance().checkSelfPermission(Manifest.permission.READ_SMS);
            int readContactsResult = MainActivity.getInstance().checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if (readSmsResult == PackageManager.PERMISSION_GRANTED && readContactsResult == PackageManager.PERMISSION_GRANTED) {
                fetchData();
            } else {
                // owncloud, onedrive, something custom, google drive...
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 0);
            }
        }).start();

        binding = FragmentMessagesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fetchData();
    }

    private void fetchData() {
        Context context = getContext();
        List<Message> messages = new Messages(context)
                .readAll(5)
                .stream()
                .filter(x -> x.getSender() != null && x.getBody() != null)
                .collect(Collectors.toList());

        MainActivity.getInstance().runOnUiThread(() -> {
            binding.loadingState.getRoot().setVisibility(View.GONE);
            if (messages.size() == 0) {
                binding.emptyState.getRoot().setVisibility(View.VISIBLE);
            } else {
                binding.regularState.messagesList.setLayoutManager(new LinearLayoutManager(context));
                binding.regularState.messagesList.setAdapter(new MessagesAdapter(context, messages));
                binding.regularState.getRoot().setVisibility(View.VISIBLE);
            }
        });
    }
}