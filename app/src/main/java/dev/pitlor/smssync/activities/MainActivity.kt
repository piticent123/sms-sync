package dev.pitlor.smssync.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.R
import dev.pitlor.smssync.databinding.ActivityMainBinding
import dev.pitlor.smssync.tasks.SmsSync
import dev.pitlor.smssync.viewmodels.BaseViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        BaseViewModel.workLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val workRequest: WorkRequest = OneTimeWorkRequest.Builder(SmsSync::class.java)
                .setConstraints(SmsSync.getConstraints(this))
                .build()
            WorkManager.getInstance(this).enqueue(workRequest)
        }

        val appBarConfiguration = AppBarConfiguration
            .Builder(R.id.navigation_messages, R.id.navigation_home, R.id.navigation_settings)
            .build()
        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)
            ?.navController
            ?: return
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(view.navView, navController)
    }
}