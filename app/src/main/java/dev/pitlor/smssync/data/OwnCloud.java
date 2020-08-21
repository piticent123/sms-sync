package dev.pitlor.smssync.data;

import com.thegrizzlylabs.sardineandroid.Sardine;
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine;

public class OwnCloud {
    private static Sardine instance;

    public static Sardine getInstance() {
        if (instance == null) {
            instance = new OkHttpSardine();
            instance.setCredentials("admin", "admin");
        }

        return instance;
    }
}