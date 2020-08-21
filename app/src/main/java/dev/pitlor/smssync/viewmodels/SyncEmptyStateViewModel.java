package dev.pitlor.smssync.viewmodels;

import android.view.View;

public class SyncEmptyStateViewModel {
    public void handleClickSync(View view) {
        view.setVisibility(View.GONE);
    }
}
