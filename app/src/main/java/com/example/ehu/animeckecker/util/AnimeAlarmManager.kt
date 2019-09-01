package com.example.ehu.animeckecker.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.ehu.animeckecker.viewmodel.NotificationAlarmViewModel
import java.util.*
import androidx.core.content.ContextCompat.createDeviceProtectedStorageContext
import com.example.ehu.animeckecker.repository.NotificationAlarmRepository
import com.example.ehu.animeckecker.room.NotificationAlarmEntity


class AnimeAlarmManager() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // 再起動した時
        // DBから、アラームを復元する
        // 通常の Application の Context に対し、Direct Boot 用の Context を生成
        var directBootContext: Context = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            context!!
        } else {
            context?.createDeviceProtectedStorageContext()!!
        }
        rebootAlarmByBD(directBootContext)
    }

    /**
     * 「分/時間前」の通知を表示します。
     * 【放送？前】 タイトル
     * 【放送[beforeTimeText]前】 [animeTitle]
     */
    fun registerNotificationAlarm(
        context: Context,
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
        var intent = Intent(context, AnimeAlarmReceiver::class.java).apply {
            putExtra(AnimeAlarmReceiver.ANIME_TITLE, animeTitle)
            putExtra(AnimeAlarmReceiver.BEFORE_TIME_TEXT, beforeTimeText)
            putExtra(AnimeAlarmReceiver.BEFORE_TIME_TEXT, beforeTimeText)
        }
        scheduleAlarm(context, notificationId, notificationStartedAt, intent)

        // 放送開始時も登録
        intent = Intent(context, AnimeAlarmReceiver::class.java).apply {
            putExtra(AnimeAlarmReceiver.IS_END_ALL_NOTIFICATION, true)
            putExtra(AnimeAlarmReceiver.ANIME_ID, animeId)
        }
        scheduleAlarm(context, animeId, startedAt, intent)
    }

    fun deleteNotificationAlarm(
        context: Context,
        notificationId: Int,
        animeId: Int,
        animeTitle: String,
        beforeTimeText: String
    ) {
        // dbから削除
        NotificationAlarmViewModel(context).deleteNotificatioAlarmByAnimeId(notificationId, animeId)
        // AlarmManagerから削除
        cancelAlarm(context, notificationId, animeTitle, beforeTimeText)
    }

    /**
     * startedAt.getTime()で表示される時間に通知する。
     */
    private fun scheduleAlarm(
        context: Context,
        notificatioId: Int,
        startedAt: Calendar,
        intent: Intent
    ) {

        //pendingIntent
        val pendingIntent =
            PendingIntent.getBroadcast(context, notificatioId, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        //alarm
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, startedAt.timeInMillis, pendingIntent)
    }

    private fun cancelAlarm(context: Context, notificationId: Int, animeTitle: String, beforeTimeText: String) {
        // intent
        val notificationIntent = Intent(context, AnimeAlarmReceiver::class.java).apply {
            putExtra(AnimeAlarmReceiver.ANIME_TITLE, animeTitle)
            putExtra(AnimeAlarmReceiver.BEFORE_TIME_TEXT, beforeTimeText)
        }
        //pendingIntent
        val pendingIntent =
            PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)
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

    private fun rebootAlarmByBD(context: Context) {
        // dbから、通知に必要な値を取得
        val alarm = NotificationAlarmRepository(context).getAllNotificationAlarm()
        val alarmGroup = alarm.groupBy { it.animeId }
        for (alr in alarmGroup) {
            // animeId Groupごと
            alr.value.forEach {
                val work = NotificationAlarmRepository(context).getAniemWorkById(it.animeId)[0]
                registerNotificationAlarm(
                    context, it.id, it.animeId, work.title,
                    work.dayOfWeek, work.hour, work.minute, work.second,
                    it.beforeSecond, it.beforeTimeText
                )
            }
        }
    }
}