package dev.pitlor.smssync.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.time.OffsetDateTime;

import dev.pitlor.smssync.databinding.FragmentSyncBinding;
import dev.pitlor.smssync.databinding.FragmentSyncNoPreviousSyncsBinding;

public class SyncFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        OffsetDateTime lastSync = null;

        if (lastSync == null) {
            FragmentSyncNoPreviousSyncsBinding view = FragmentSyncNoPreviousSyncsBinding.inflate(inflater);
            return view.getRoot();
        } else {
            FragmentSyncBinding view = FragmentSyncBinding.inflate(inflater);
            return view.getRoot();
        }
    }
}