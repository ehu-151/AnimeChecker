package com.example.ehu.animeckecker.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AnimeAlarmReceiver : BroadcastReceiver() {
    companion object {
        const val ANIME_TITLE = "notification_id"
        const val NOTIFICATION_TIME = "notification_time"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val animeTitle = intent.getStringExtra(ANIME_TITLE)
        val time = intent.getStringExtra(NOTIFICATION_TIME)
        AnimeNotification(context).notifyThisSeasonBroadcast(animeTitle, time)
    }
}