package com.mamorasoft.app.frameworkbenchmark.helper

import androidx.core.app.NotificationCompat
import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.TypedValue
import androidx.core.app.NotificationManagerCompat
import com.mamorasoft.app.frameworkbenchmark.views.MainActivity


class NotificationHelper {
    companion object{
        @SuppressLint("Recycle")
        fun showNotification(context: Context){
            val typedValue = TypedValue()

            val notificationManager = NotificationManagerCompat.from(context)
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
// notificationId is a unique int for each notification that you must define
            createNotificationChannel(context)
            var builder = NotificationCompat.Builder(context, "woi")
                    .setSmallIcon(context.applicationInfo.icon)
                    .setColor((context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary)).getColor(0, 0)))
                    .setContentTitle("My notification")
                    .setContentText("Much longer text that cannot fit one line...")
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText("Much longer text that cannot fit one line..." +
                                    "................................................." +
                                    "..............................................." +
                                    "............................................"))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_MAX)

            notificationManager.notify(12432124, builder.build())


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