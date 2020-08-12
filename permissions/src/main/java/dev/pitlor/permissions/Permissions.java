package dev.pitlor.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Permissions {
    @NonNull Activity activity;

    private List<Integer> results;
    private CountDownLatch latch;

    public void assertPermission(String... permissions) {
        try {
            if (latch == null || latch.getCount() == 0) {
                latch = new CountDownLatch(1);
            } else {
                throw new Exception("2 places are asking for permissions at once!");
            }

            new Thread(() -> checkForPermission(permissions)).start();
            latch.await();

            for (Integer result : results) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    throw new Exception("Not all permissions were granted");
                }
            }

        } catch (Exception e) {
            onPermissionFailed();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        System.out.println("Got results");
        List<Integer> r = new ArrayList<>();
        for (int grantResult : grantResults) {
            r.add(grantResult);
        }

        results = r;
        latch.countDown();
    }

    private void onPermissionFailed() {
        System.out.println("I didn't get permissions :(");
    }

    private void checkForPermission(String... permissions) {
        int[] results = new int[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            results[i] = activity.checkSelfPermission(permissions[i]);
        }

        boolean allPermissionsGranted = true;
        for (int result : results) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                break;
            }
        }

        if (allPermissionsGranted) {
            this.results = new ArrayList<>();
            latch.countDown();
            return;
        }

        List<String> permissionsNotYetGranted = new ArrayList<>();
        for (int i = 0; i < results.length; i++) {
            if (results[i] != PackageManager.PERMISSION_GRANTED) {
                permissionsNotYetGranted.add(permissions[i]);
            }
        }

        ActivityCompat.requestPermissions(activity, Utilities.FromList(permissionsNotYetGranted), 0);
    }
}
