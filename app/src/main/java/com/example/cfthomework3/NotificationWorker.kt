package com.example.cfthomework3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class NotificationWorker(var mContext: Context, workerParams: WorkerParameters) :
    Worker(mContext, workerParams) {

    private var notificationManager: NotificationManager? = null

    override fun doWork(): Result {
        sendNotification("CustomTimer", "time left to waste:  " +
                CustomTimer.timeLeftFormatted, 1)
        return Result.success()
    }

    private fun sendNotification(
        title: String,
        text: String,
        id: Int
    ) {
        val intent = Intent(mContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        createNotificationChannel()
        val builder = NotificationCompat.Builder(mContext, "default")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        Objects.requireNonNull(notificationManager)?.notify(id, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "default"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("default", name, importance)
            notificationManager =
                mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager!!.createNotificationChannel(channel)
        }
    }

}