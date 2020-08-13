package dev.pitlor.smssync.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import dev.pitlor.smssync.R;
import dev.pitlor.smssync.viewModels.MessagesViewModel;

public class MessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        messagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}