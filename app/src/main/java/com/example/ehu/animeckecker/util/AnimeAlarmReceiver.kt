package com.example.ehu.animeckecker.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.ehu.animeckecker.viewmodel.NotificationAlarmViewModel

class AnimeAlarmReceiver : BroadcastReceiver() {
    companion object {
        /** 通知id */
        const val NOTIFICATION_ID = "notification_id"
        /** アニメのタイトル */
        const val ANIME_TITLE = "anime_title"
        /**【放送[BEFORE_TIME_TEXT]前】用テキスト */
        const val BEFORE_TIME_TEXT = "before_time_text"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        val animeTitle = intent.getStringExtra(ANIME_TITLE)
        val beforeTimeText = intent.getStringExtra(BEFORE_TIME_TEXT)
        AnimeNotification(context).notifyThisSeasonBroadcast(animeTitle, beforeTimeText)

        // 通知をdbから削除する。
        NotificationAlarmViewModel(context).deleteNotificatioAlarm(notificationId)
    }
}