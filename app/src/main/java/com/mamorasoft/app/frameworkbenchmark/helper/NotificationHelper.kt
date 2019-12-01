package com.mamorasoft.app.frameworkbenchmark.helper

import androidx.core.app.NotificationCompat
import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.util.TypedValue
import androidx.core.app.NotificationManagerCompat


class NotificationHelper {
    companion object {
        val TITLE = "title"
        val CONTENT = "content"
        val SMALL_ICON = "smallIcon"
        val LARGE_ICON = "largeIcon"
        val NOTIF_ID = "notifID"
        val FULL_CONTENT = "full-content"
//        val CHANNEL_ID = "channel_container"

        @SuppressLint("Recycle")
        fun showNotification(context: Context, opts: HashMap<String, Any>, pendingIntent: PendingIntent) {
            val title = opts.get(TITLE) as String
            val content = opts.get(CONTENT) as String
            val full_content = opts.get(FULL_CONTENT) as String
//            var smallIcon = opts.get(SMALL_ICON) as Int
//            var largeIcon = opts.get(LARGE_ICON) as Bitmap
            val notifID = opts.get(NOTIF_ID) as Int
            val typedValue = TypedValue()

            val notificationManager = NotificationManagerCompat.from(context)
// notificationId is a unique int for each notification that you must define
            createNotificationChannel(context)
            val builder = NotificationCompat.Builder(context, "woi")
                    .setSmallIcon(context.applicationInfo.icon)
                    .setColor((context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary)).getColor(0, 0)))
                    .setContentTitle(title)
                    .setContentText(content)
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(full_content))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_MAX)

            notificationManager.notify(notifID, builder.build())


        }

        private fun createNotificationChannel(context: Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "woi"
                val descriptionText = "desc"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel("woi", name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

            }
        }

    }
}