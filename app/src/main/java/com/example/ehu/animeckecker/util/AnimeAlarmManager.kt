package com.example.ehu.animeckecker.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.ehu.animeckecker.viewmodel.NotificationAlarmViewModel
import java.util.*

class AnimeAlarmManager(private val context: Context) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 「分/時間前」の通知を表示します。
     * 【放送？前】 タイトル
     * 【放送[beforeTimeText]前】 [animeTitle]
     */
    fun registerNotificationAlarm(
        notificationId: Int, animeId: Int, animeTitle: String,
        dayOfWeek: Int, hour: Int, minute: Int, second: Int,
        beforeSecond: Int, beforeTimeText: String
    ) {
        //  dayOfWeek, hour, minute, second をCalendarに変換
        val startedAt = toCalendar(dayOfWeek, hour, minute, second)
        Log.d("app_alarm_start", startedAt.getTime().toString())
        val notificationStartedAt = toCalendar(dayOfWeek, hour, minute, second).apply {
            add(Calendar.SECOND, -beforeSecond)
        }
        Log.d("app_alarm_start_no", notificationStartedAt.getTime().toString())

        // dbに保存する
        NotificationAlarmViewModel(context).insertNotificationAlarm(
            notificationId, animeId, animeTitle,
            dayOfWeek, hour, minute, second,
            beforeSecond, beforeTimeText
        )
        // AlarmManagerに登録
        scheduleAlarm(notificationId, animeTitle, beforeTimeText, notificationStartedAt)
    }

    fun deleteNotificationAlarm(notificationId: Int, animeTitle: String, beforeTimeText: String) {
        // dbから削除
        NotificationAlarmViewModel(context).deleteNotificatioAlarm(notificationId)
        // AlarmManagerから削除
        cancelAlarm(notificationId, animeTitle, beforeTimeText)
    }

    /**
     * startedAt.getTime()で表示される時間に通知する。
     */
    private fun scheduleAlarm(notificatioId: Int, animeTitle: String, beforeTimeText: String, startedAt: Calendar) {
        // intent
        val notificationIntent = Intent(context, AnimeAlarmReceiver::class.java).apply {
            putExtra(AnimeAlarmReceiver.NOTIFICATION_ID, notificatioId)
            putExtra(AnimeAlarmReceiver.ANIME_TITLE, animeTitle)
            putExtra(AnimeAlarmReceiver.BEFORE_TIME_TEXT, beforeTimeText)
        }
        //pendingIntent
        val pendingIntent =
            PendingIntent.getBroadcast(context, notificatioId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //alarm
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, startedAt.timeInMillis, pendingIntent)
    }

    private fun cancelAlarm(notificationId: Int, animeTitle: String, beforeTimeText: String) {
        // intent
        val notificationIntent = Intent(context, AnimeAlarmReceiver::class.java).apply {
            putExtra(AnimeAlarmReceiver.ANIME_TITLE, animeTitle)
            putExtra(AnimeAlarmReceiver.BEFORE_TIME_TEXT, beforeTimeText)
        }
        //pendingIntent
        val pendingIntent =
            PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        pendingIntent.cancel()
        //alarm
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun toCalendar(dayOfWeek: Int, hour: Int, minute: Int, second: Int): Calendar {
        val c = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
            set(Calendar.DAY_OF_WEEK, dayOfWeek)
        }
        // 直近の曜日にする
        val now = Calendar.getInstance()
        if (now.timeInMillis - c.timeInMillis > 0) {
            c.add(Calendar.DATE, 7)
        }
        return c
    }
}