package com.salazarev.hw26servisenotification

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat


class ServiceWorker: Service(){

    companion object{
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "CHANNEL_ID_1"
        const val ACTION_CLOSE = "SERVICE_ACTION_CLOSE"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Название"
            val description = "Описание"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val deleteIntent = Intent(this, ServiceWorker::class.java)
        deleteIntent.action = ACTION_CLOSE
        val deletePendingIntent = PendingIntent.getService(this, 0, deleteIntent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)

        builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.service_title))
            .setContentText("Текст уведомления")
            .setOnlyAlertOnce(true)
            .addAction(0, "STOP SERVICE", deletePendingIntent)

        return builder.build()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action){
            ACTION_CLOSE -> stopSelf()
            else -> {
                startForeground(NOTIFICATION_ID, createNotification())
                Toast.makeText(this, "Сервис запущен", Toast.LENGTH_SHORT).show()
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Сервис остановлен", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(p0: Intent?): IBinder? {
       return null
    }
}