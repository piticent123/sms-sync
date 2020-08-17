package dev.pitlor.smssync.activities.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import dev.pitlor.smssync.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}