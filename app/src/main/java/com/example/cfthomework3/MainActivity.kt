package com.example.cfthomework3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val DELAY_IN_SECONDS = 5L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, TimerFragment.newInstance(this))
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiresStorageNotLow(true)
            .build()

        val request: WorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setConstraints(constraints)
            .setInitialDelay(DELAY_IN_SECONDS, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(request)
    }
}