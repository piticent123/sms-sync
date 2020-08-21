package dev.pitlor.smssync.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.pitlor.smssync.databinding.FragmentSyncBinding;
import dev.pitlor.smssync.viewmodels.SyncFragmentViewModel;

public class SyncFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SyncFragmentViewModel viewModel = new ViewModelProvider(this).get(SyncFragmentViewModel.class);
        FragmentSyncBinding view = FragmentSyncBinding.inflate(inflater);

        view.setViewModel(viewModel);
        return view.getRoot();
    }
}