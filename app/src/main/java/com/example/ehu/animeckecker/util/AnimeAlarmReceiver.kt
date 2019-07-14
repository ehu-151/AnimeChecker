package com.example.ehu.animeckecker.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.ehu.animeckecker.repository.NotificationAlarmRepository

class AnimeAlarmReceiver : BroadcastReceiver() {
    companion object {
        /** 通知id */
        const val NOTIFICATION_ID = "notification_id"
        /** アニメのタイトル */
        const val ANIME_TITLE = "anime_title"
        /**【放送[BEFORE_TIME_TEXT]前】用テキスト */
        const val BEFORE_TIME_TEXT = "before_time_text"
        /**　今週のそのアニメの通知が終わったか　*/
        const val IS_END_ALL_NOTIFICATION = "is_end_all_notification"
        /**　animeId　*/
        const val ANIME_ID = "anime_id"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        val animeTitle = intent.getStringExtra(ANIME_TITLE)
        val beforeTimeText = intent.getStringExtra(BEFORE_TIME_TEXT)
        val is_end = intent.getBooleanExtra(IS_END_ALL_NOTIFICATION, false)
        // Notificationを通知する
        AnimeNotification(context).notifyThisSeasonBroadcast(animeTitle, beforeTimeText)
        // 今週のそのアニメの通知が終わったので、Alarmを再登録
        if (is_end) {
            val animeId = intent.getIntExtra(ANIME_ID, 0)
            // dbから、そのアニメのAlarmを取得
            NotificationAlarmRepository(context).getAllNotificationAlarmByAnimeId(animeId)
            // alarmを登録

        }
    }
}