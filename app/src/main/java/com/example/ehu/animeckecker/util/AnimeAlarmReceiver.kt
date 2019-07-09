package com.example.ehu.animeckecker.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AnimeAlarmReceiver : BroadcastReceiver() {
    companion object {
        /** アニメのタイトル */
        const val ANIME_TITLE = "notification_id"
        /**【放送[BEFORE_TIME_TEXT]前】用テキスト */
        const val BEFORE_TIME_TEXT = "notification_time"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val animeTitle = intent.getStringExtra(ANIME_TITLE)
        val beforeTimeText = intent.getStringExtra(BEFORE_TIME_TEXT)
        AnimeNotification(context).notifyThisSeasonBroadcast(animeTitle, beforeTimeText)
    }
}