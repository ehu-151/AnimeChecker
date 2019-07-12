package com.example.ehu.animeckecker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.repository.NotificationAlarmRepository
import com.example.ehu.animeckecker.room.NotificationAlarmEntity

class MyNotificationViewModel() : ViewModel() {
    private val _notifyInfo: MutableLiveData<List<NotificationAlarmEntity>> = MutableLiveData()
    val notifyInfo: LiveData<List<NotificationAlarmEntity>> = _notifyInfo

    fun loadNotifyInfo(context: Context) {
        _notifyInfo.postValue(NotificationAlarmRepository(context).getAllNotificationAlarm())
    }

}