package com.example.alarmapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat


class AlarmReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel  = NotificationChannel(
                "alarm_channel",
                "Alarm Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
            val builder = NotificationCompat.Builder(context,"alarm_channel")
                .setContentTitle("Rohit Rajguru")
                .setContentText("This is Sheducled Alaram")
                .setSmallIcon(R.drawable.notifications)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        manager.notify(1,builder.build())

    }
}