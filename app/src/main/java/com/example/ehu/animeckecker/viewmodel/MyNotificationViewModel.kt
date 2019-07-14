package com.example.ehu.animeckecker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.MyNotificationRow
import com.example.ehu.animeckecker.repository.NotificationAlarmRepository
import com.example.ehu.animeckecker.room.NotificationAlarmEntity
import java.util.*

class MyNotificationViewModel() : ViewModel() {
    private val _row: MutableLiveData<List<MyNotificationRow>>? = MutableLiveData()
    val row: LiveData<List<MyNotificationRow>>? = _row

    fun loadNotifyInfo(context: Context) {
        val alarms = NotificationAlarmRepository(context).getAllNotificationAlarm()

        if (alarms.isEmpty()) {
            // 空の場合
            _row?.value = null
        } else {
            setRow(context, alarms)
        }

    }

    private fun setRow(context: Context, alarms: List<NotificationAlarmEntity>) {
        // 表示用配列
        val notificationalarm: MutableList<MyNotificationRow> = mutableListOf()

        // animeIdでグループ化
        alarms.groupBy { it.animeId }.map {
            val animeId = it.value[0].animeId
            val id = it.value.map { it.id }
            // animeIdごとにWorkを取得
            NotificationAlarmRepository(context).getAniemWorkById(animeId).map { anime ->
                // timeの配列
                var time: MutableMap<Int, String> = mutableMapOf()
                for (alarm in it.value) {
                    time.put(alarm.beforeSecond, alarm.beforeTimeText)
                }
                // 表示用配列のadd
                notificationalarm.add(
                    MyNotificationRow(
                        id = id.toMutableList(), animeId = animeId, animeTitle = anime.title,
                        dayOfWeek = anime.dayOfWeek, dayOfWeekText = anime.dayOfWeek.toString(),
                        hour = anime.hour, minute = anime.minute, second = anime.second,
                        startAtText = toTime(anime.dayOfWeek, anime.hour, anime.minute, anime.second),
                        time = time
                    )
                )
            }


        }
        _row?.postValue(notificationalarm)

    }

    private fun toTime(dayOfWeek: Int, hour: Int, minute: Int, second: Int): String {
        val week = when (dayOfWeek) {
            Calendar.MONDAY -> "月"
            Calendar.TUESDAY -> "火"
            Calendar.WEDNESDAY -> "水"
            Calendar.THURSDAY -> "木"
            Calendar.FRIDAY -> "金"
            Calendar.SATURDAY -> "土"
            Calendar.SUNDAY -> "日"
            else -> ""
        }
        var time = "$week/${hour.zeroFill()}:${minute.zeroFill()}"

        if (second != 0) {
            time += ":${second.zeroFill()}"
        }
        return time
    }

    fun Int.zeroFill(): String {
        return String.format("%02d", this)
    }
}