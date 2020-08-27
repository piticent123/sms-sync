package dev.pitlor.smssync.activities.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import dagger.hilt.android.AndroidEntryPoint;
import dev.pitlor.smssync.R;

@AndroidEntryPoint
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}