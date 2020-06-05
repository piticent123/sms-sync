package dev.pitlor.smssync.util;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtilities {
    public static boolean AssertPermission(final Activity activity, String[] permissions, int code) {
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

        if (allPermissionsGranted) return true;
        List<String> permissionsNotYetGranted = new ArrayList<>();
        for (int i = 0; i < results.length; i++) {
            if (results[i] != PackageManager.PERMISSION_GRANTED) {
                permissionsNotYetGranted.add(permissions[i]);
            }
        }

        ActivityCompat.requestPermissions(activity, Utilities.FromList(permissionsNotYetGranted), code);
        return false;
    }

    public static boolean AssertPermission(Activity activity, String permission, int code) {
        return AssertPermission(activity, new String[]{permission}, code);
    }
}
