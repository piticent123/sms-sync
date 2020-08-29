package dev.pitlor.smssync.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import dev.pitlor.smssync.databinding.FragmentSyncBinding;
import dev.pitlor.smssync.viewmodels.SyncEmptyStateViewModel;
import dev.pitlor.smssync.viewmodels.SyncFragmentViewModel;
import dev.pitlor.smssync.viewmodels.SyncRegularViewModel;

@AndroidEntryPoint
public class SyncFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSyncBinding view = FragmentSyncBinding.inflate(inflater);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);

        view.setViewModel(viewModelProvider.get(SyncFragmentViewModel.class));
        view.emptyState.setViewModel(viewModelProvider.get(SyncEmptyStateViewModel.class));
        view.regularState.setViewModel(viewModelProvider.get(SyncRegularViewModel.class));

        return view.getRoot();
    }
}