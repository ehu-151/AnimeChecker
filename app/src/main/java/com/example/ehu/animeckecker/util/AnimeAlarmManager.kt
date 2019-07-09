package com.example.ehu.animeckecker.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class AnimeAlarmManager(private val context: Context): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 「分/時間前」の通知を表示します。
     * 【放送？前】 タイトル
     * 【放送[beforeTimeText]前】 [animeTitle]
     */
    fun registerNotificationAlarm(animeTitle: String, startedAt: Calendar, beforeSecond: Int, beforeTimeText: String) {
        startedAt.add(Calendar.SECOND, -beforeSecond)
        scheduleNotification(animeTitle, beforeTimeText, startedAt)
    }

    /**
     * startedAt.getTime()で表示される時間に通知する。
     */
    private fun scheduleNotification(animeTitle: String, beforeTimeText: String, startedAt: Calendar) {
        // intent
        val notificationIntent = Intent(context, AnimeAlarmReceiver::class.java).apply {
            putExtra(AnimeAlarmReceiver.ANIME_TITLE, animeTitle)
            putExtra(AnimeAlarmReceiver.BEFORE_TIME_TEXT, beforeTimeText)
        }
        //pendingIntent
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //alarm
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, startedAt.timeInMillis, pendingIntent)
    }
}