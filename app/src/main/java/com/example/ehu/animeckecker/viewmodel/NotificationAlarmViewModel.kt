package com.example.ehu.animeckecker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.repository.NorificationAlarmRepository
import com.example.ehu.animeckecker.room.NotificationAlarmEntity

class NotificationAlarmViewModel(private val context: Context) : ViewModel() {
    private val repository = NorificationAlarmRepository(context)
    private val _notificationAlarm: MutableLiveData<List<NotificationAlarmEntity>> = MutableLiveData()
    val notificatioAlarm: LiveData<List<NotificationAlarmEntity>> = _notificationAlarm

    fun getNotificationAlarm(): MutableLiveData<List<NotificationAlarmEntity>> {
        _notificationAlarm.postValue(repository.getAllNotificationAlarm())
        return _notificationAlarm
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
    }
}