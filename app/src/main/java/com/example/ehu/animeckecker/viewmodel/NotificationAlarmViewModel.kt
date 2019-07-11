package com.example.ehu.animeckecker.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.repository.NorificationAlarmRepository
import com.example.ehu.animeckecker.room.NotificationAlarmEntity

class NotificationAlarmViewModel(private val context: Context) : ViewModel() {
    private val repository = NorificationAlarmRepository(context)
    private val notificationAlarm: MutableLiveData<List<NotificationAlarmEntity>> = MutableLiveData()

    fun getNotificationAlarm(): MutableLiveData<List<NotificationAlarmEntity>> {
        notificationAlarm.postValue(repository.getAllNotificationAlarm())
        return notificationAlarm
    }

    fun insertNotificationAlarm(
        notificatioId: Int,
        animeId: Int,
        beforeSecond: Int,
        beforeTimeText: String,
        dayOfWeek: Int,
        dayOfWeekText: String,
        hour: Int,
        minute: Int,
        second: Int
    ) {
        repository.insertNotificationAlarm(notificatioId, animeId, beforeSecond, beforeTimeText)
        repository.getAllNotificationAlarm()
    }
}
