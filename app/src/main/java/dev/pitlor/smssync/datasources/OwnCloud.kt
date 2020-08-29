package dev.pitlor.smssync.datasources

import com.thegrizzlylabs.sardineandroid.Sardine
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine

object OwnCloud {
    var instance: Sardine? = null
        get() {
            if (field == null) {
                field = OkHttpSardine()
                (field as OkHttpSardine).setCredentials("admin", "admin")
            }
            return field
        }
        private set
}