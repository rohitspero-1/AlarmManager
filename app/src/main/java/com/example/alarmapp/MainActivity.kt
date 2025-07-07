package com.example.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.contentcapture.ContentCaptureSession
import android.widget.Button
import android.widget.CalendarView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var timepicker : TimePicker
    private lateinit var btnSetAlarm : Button
    private lateinit var btnCancelAlarm : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requstNotificationPermissionIfNeeded()

        timepicker = findViewById(R.id.timePicker)
        btnSetAlarm = findViewById(R.id.btnSetAlarm)
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm)

        btnSetAlarm.setOnClickListener{
            val canlender = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY,timepicker.hour)
                set(Calendar.MINUTE,timepicker.minute)
                set(Calendar.SECOND,0)
                if (before(Calendar.getInstance())){
                    add(Calendar.DATE,1)
                }
            }
            val intent = Intent(this,AlarmReceiver::class.java)
            val pendinngIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                canlender.timeInMillis,
                pendinngIntent
            )
            Toast.makeText(this,"Alarm set ${timepicker.hour}:${timepicker.minute}",Toast.LENGTH_LONG).show()
        }
        btnCancelAlarm.setOnClickListener {
            val intent = Intent(this,AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this,"Alarm Cancelled",Toast.LENGTH_LONG).show()
        }




    }
    private fun requstNotificationPermissionIfNeeded(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),1001)
            }
        }
    }
}