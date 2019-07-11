package com.example.ehu.animeckecker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.repository.NorificationAlarmRepository
import com.example.ehu.animeckecker.room.NotificationAlarmEntity

class NotificationAlarmViewModel(private val repository: NorificationAlarmRepository) : ViewModel() {
    private val notificationAlarm: MutableLiveData<List<NotificationAlarmEntity>> = MutableLiveData()

    fun getNotificationAlarm(): MutableLiveData<List<NotificationAlarmEntity>> {
        notificationAlarm.postValue(repository.getAllNotificationAlarm())
        return notificationAlarm
    }
}
