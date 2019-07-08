package com.example.ehu.animeckecker.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class AnimeNotification(private val context: Context) {
    private val CHANNEL_ID = "this_season_notification"
    private val NOTIFICATION_TEXT_ID = 1
    private val icon_res = android.R.drawable.sym_def_app_icon

    // カテゴリー名（通知設定画面に表示される情報）
    private val channelName = "今期のアニメ"
    // 通知の詳細情報（通知設定画面に表示される情報）
    private val notifyDescription = "放送時間の通知"

    /**
     *  今期のアニメの放送を通知する
     *  例：\[放送3分前] AnimeTitle
     */
    fun notifyThisSeasonBroadcast(animeTitle: String, time: String) {
        val nowTitle = "[放送${time}前] $animeTitle"
        notify(nowTitle)
    }

    private fun notify(title: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // API26以上はChannelの指定
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                val mChannel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH)
                mChannel.apply {
                    description = notifyDescription
                }
                manager.createNotificationChannel(mChannel)
            }
        }
        manager.notify(NOTIFICATION_TEXT_ID, getNotificationBuilder(title))
    }

    private fun getNotificationBuilder(title: String): Notification {
        val builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, CHANNEL_ID)
            } else {
                Notification.Builder(context)
            }
        builder.apply {
            setSmallIcon(icon_res)
            setContentTitle(title)
            setShowWhen(true)
        }
        return builder.build()
    }
}