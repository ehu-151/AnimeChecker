package com.example.ehu.animeckecker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.repository.NotificationAlarmRepository
import com.example.ehu.animeckecker.room.NotificationAlarmEntity

class MyNotificationViewModel() : ViewModel() {
    private val _notificationAlarm: MutableLiveData<List<NotificationAlarmEntity>> = MutableLiveData()
    val notificationAlarm: LiveData<List<NotificationAlarmEntity>> = _notificationAlarm

    fun loadNotifyInfo(context: Context) {
        _notificationAlarm.postValue(NotificationAlarmRepository(context).getAllNotificationAlarm())
    }

}