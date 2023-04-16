package com.example.flutter_notif_on_kill_poc

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import io.flutter.Log

class NotificationOnKillService: Service() {
    // Declare variables to store the notification title and description.
    private lateinit var title: String
    private lateinit var description: String

    // This function is called when the service is started.
    // It gets the title and description from the intent, or sets default values if they are not provided.
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        title = intent?.getStringExtra("title") ?: "Default title"
        description = intent?.getStringExtra("description") ?: "Default body"

        return START_STICKY
    }

    // This function is called when the app is killed, and will display the notification.
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTaskRemoved(rootIntent: Intent?) {
        try {
            // Create an intent to open the application when the notification is clicked.
            val notificationIntent = packageManager.getLaunchIntentForPackage(packageName)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

            // Build the notification with its content, priority, and the pending intent.
            val notificationBuilder = NotificationCompat.Builder(this, "com.example.flutter_notif_on_kill_poc")
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)

            // Create the notification channel with a name, description, and importance level.
            val name = "Notification service on application kill"
            val descriptionText = "Explain here why you need this notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("com.example.flutter_notif_on_kill_poc", name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system and show the notification.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(123, notificationBuilder.build())
        } catch (e: Exception) {
            // Log any errors that occur while showing the notification.
            Log.d("NotificationOnKillService", "Error showing notification", e)
        }
        super.onTaskRemoved(rootIntent)
    }

    // This function is required, but onBind is not used in this service, so it returns null.
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
