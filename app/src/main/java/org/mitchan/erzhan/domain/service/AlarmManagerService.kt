package org.mitchan.erzhan.domain.service

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import org.mitchan.erzhan.R
import org.mitchan.erzhan.domain.singleton.AppServiceSingleton
import java.time.Instant

object AlarmManagerService {
    fun setExactAlarm(
        context: Context,
        triggerAtMillis: Long,
    ) {
        val currentTime = Instant.now().toEpochMilli()

        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (manager.canScheduleExactAlarms()) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                727,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (triggerAtMillis < currentTime || pendingIntent == null) {
                return
            }

            AlarmManagerCompat.setExactAndAllowWhileIdle(
                manager,
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        } else {
            Log.e("erzhan_alarm", "Can't set up alarms!")
        }
    }
}

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("erzhan_alarm", "Caught!")
        val serviceIntent = Intent(context, AlarmService::class.java)
        ContextCompat.startForegroundService(context, serviceIntent)
    }
}

class AlarmService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(1, notification)

//        val context = AppServiceSingleton.getInstanceUnsafe().context
//        val fullscreenIntent = Intent(context, AlarmActivity::class.java)
//            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        fullscreenIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(fullscreenIntent)

        return START_NOT_STICKY
    }

    private val dismissReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            stopSelf()
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Register the receiver
        registerReceiver(dismissReceiver, IntentFilter("org.mitchanx.erzhan.DISMISS_ALARM"))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver
        unregisterReceiver(dismissReceiver)
    }


    private fun createNotification(): Notification {
        val channelId = "alarm_service_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Alarm Service",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val context = AppServiceSingleton.getInstanceUnsafe().context
        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Alarm is going off")
            .setContentText("Tap to open the app")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(pendingIntent, true)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

class AlarmActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setShowWhenLocked(true)
        this.setTurnScreenOn(true)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }

        this.onBackPressedDispatcher.addCallback(this, callback)

        setContent {
            AlarmScreen(onDismiss = {
                sendBroadcast(Intent("org.mitchanx.erzhan.DISMISS_ALARM"))
                finish()
            })
        }
    }

    @Composable
    fun AlarmScreen(onDismiss: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Alarm!",
                    color = Color.White,
                    fontSize = 48.sp
                )
                Button(onClick = onDismiss ) { Text("Stop") }
            }
        }
    }
}