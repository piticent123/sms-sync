package dev.pitlor.smssync.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.pitlor.smssync.databinding.FragmentMessagesBinding;
import dev.pitlor.smssync.viewmodels.MessageFragmentViewModel;
import dev.pitlor.smssync.viewmodels.MessageRegularViewModel;

public class MessagesFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMessagesBinding binding = FragmentMessagesBinding.inflate(inflater);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);

        binding.setViewModel(viewModelProvider.get(MessageFragmentViewModel.class));
        binding.regularState.setViewModel(viewModelProvider.get(MessageRegularViewModel.class));

        return binding.getRoot();
    }
}