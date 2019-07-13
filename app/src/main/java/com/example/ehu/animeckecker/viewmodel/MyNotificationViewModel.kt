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

        var time: MutableMap<Int, String> = mutableMapOf()
        for (alarm in alarms) {
            time.put(alarm.beforeSecond, alarm.beforeTimeText)
        }

        val notificationalarm: MutableList<MyNotificationRow> = mutableListOf()
        alarms.groupBy { it.animeId }.map {
            val list= it.value
            val animeId= it.value[0].animeId
            val id=it.value[0].id
            NotificationAlarmRepository(context).getAniemWorkById(animeId).map { anime ->
                notificationalarm.add(
                    MyNotificationRow(
                        id = id, animeId = animeId, animeTitle = anime.Title,
                        dayOfWeek = anime.dayOfWeek, dayOdWeekText = anime.dayOfWeek.toString(),
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