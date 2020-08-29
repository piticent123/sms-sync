package dev.pitlor.smssync

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SmsSyncApplication : Application(), Configuration.Provider {
    @JvmField
    @Inject
    var workerFactory: HiltWorkerFactory? = null

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory!!)
            .build()
    }
}