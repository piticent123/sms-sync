package dev.pitlor.smssync.fragments;

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
import dev.pitlor.smssync.adapters.MessagesAdapter;
import dev.pitlor.smssync.databinding.FragmentMessagesBinding;
import dev.pitlor.smssync.databinding.FragmentMessagesNoMessagesBinding;

public class MessagesFragment extends Fragment {
    List<Message> messageList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Messages messages = new Messages(getContext());
        messageList = messages
            .readAll(5)
            .stream()
            .filter(x -> x.getSender() != null && x.getBody() != null)
            .collect(Collectors.toList());

        if (messageList.size() == 0) {
            FragmentMessagesNoMessagesBinding view = FragmentMessagesNoMessagesBinding.inflate(inflater);
            return view.getRoot();
        } else {
            FragmentMessagesBinding view = FragmentMessagesBinding.inflate(inflater);
            view.messages.setLayoutManager(new LinearLayoutManager(getContext()));
            view.messages.setAdapter(new MessagesAdapter(getContext(), messageList));
            return view.getRoot();
        }
    }
}