package com.haithamghanem.locationtask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

val VERBOSE_NOTIFICATION_CHANNEL_NAME_P: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION_P =
    "Shows notifications whenever work starts"
const val CHANNEL_ID_P = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID_P = 2
class PeriodicWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        try {

            var hasReachedDestination = inputData.getBoolean(MainFragment.WORKER_DATA, false)
            if (hasReachedDestination){
                makeStatusNotification("Location Update", "You have reached your destination", applicationContext)
            }else{
                makeStatusNotification("Location Update", "You are still away from your destination, Keep moving!", applicationContext)
            }

            return Result.success()
        }catch (e : Exception){
            return Result.failure()
        }

    }

    private fun makeStatusNotification(title: String, message: String, context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME_P
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION_P
            val channel = NotificationChannel(
                CHANNEL_ID_P,
                name,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID_P)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_P, builder.build())
    }
}