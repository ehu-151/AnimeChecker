package com.example.ehu.animeckecker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.MyNotificationRow
import com.example.ehu.animeckecker.repository.NotificationAlarmRepository
import java.util.*

class MyNotificationViewModel() : ViewModel() {
    private val _row: MutableLiveData<List<MyNotificationRow>> = MutableLiveData()
    val row: LiveData<List<MyNotificationRow>> = _row

    fun loadNotifyInfo(context: Context) {
        NotificationAlarmRepository(context).getAllNotificationAlarm().forEach { alarm ->

            val time = mapOf(alarm.beforeSecond to alarm.beforeTimeText)
            _row.postValue(NotificationAlarmRepository(context).getAniemWorkById(alarm.animeId).map { anime ->
                MyNotificationRow(
                    id = alarm.id, animeId = alarm.animeId, animeTitle = anime.Title,
                    dayOfWeek = anime.dayOfWeek, dayOdWeekText = anime.dayOfWeek.toString(),
                    hour = anime.hour, minute = anime.minute, second = anime.second,
                    startAtText = toTime(anime.dayOfWeek, anime.hour, anime.minute, anime.second),
                    time = time
                )
            })


        }
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