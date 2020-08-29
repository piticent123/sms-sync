package dev.pitlor.smssync

import androidx.work.Data
import androidx.work.Worker
import dev.pitlor.smssync.tasks.SmsSync

fun Worker.setProgress(progress: String) {
    setProgressAsync(Data.Builder().putString(SmsSync.PROGRESS, progress).build())
}