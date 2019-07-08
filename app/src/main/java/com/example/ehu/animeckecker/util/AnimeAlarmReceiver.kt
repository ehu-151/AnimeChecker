package com.example.ehu.animeckecker.util

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast

class AnimeAlarmReceiver : BroadcastReceiver() {
    companion object {
        val NOTIFICATION_ID = "notification_id"
        val NOTIFICATION_CONTENT = "notification_content"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val nManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val nId = intent?.getIntExtra(NOTIFICATION_ID, 0)
        val content = intent?.getStringExtra(NOTIFICATION_CONTENT)
        Toast.makeText(context, "kkkkkk", Toast.LENGTH_SHORT).show()
//        nManager.notify(nId, buildNotification(context, content))
    }

    private fun buildNotification(context: Context, content: String): Notification {
        // TODO:後でチャンネルidを指定
        val builder: Notification.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, "1")
            } else {
                Notification.Builder(context)
            }
        builder.setContentTitle("Notification!!")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)

        return builder.build()
    }

}