package com.shustreek.otustodo.receive

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.shustreek.otustodo.MainActivity
import com.shustreek.otustodo.R

class NotifyBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notify = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notify.getNotificationChannel(CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                notify.createNotificationChannel(channel)
            }
        }
        val contentIntent = PendingIntent.getActivity(
            context,
            0, Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(intent.getStringExtra(EXT_NAME))
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentIntent(contentIntent)
            .build()

        notify.notify(intent.getIntExtra(EXT_ID, 0), notification)

    }

    companion object {
        private const val CHANNEL_ID = "todo_notify"

        const val EXT_ID = "ext_id"
        const val EXT_NAME = "ext_name"
    }

}