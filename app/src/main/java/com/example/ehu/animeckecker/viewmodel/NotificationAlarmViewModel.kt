package com.example.ehu.animeckecker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.repository.NotificationAlarmRepository
import com.example.ehu.animeckecker.room.NotificationAlarmEntity

class NotificationAlarmViewModel(private val context: Context) : ViewModel() {
    private val repository = NotificationAlarmRepository(context)
    private val _notificationAlarm: MutableLiveData<List<NotificationAlarmEntity>> = MutableLiveData()
    val notificatioAlarm: LiveData<List<NotificationAlarmEntity>> = _notificationAlarm

    fun getNotificationAlarm(): MutableLiveData<List<NotificationAlarmEntity>> {
        _notificationAlarm.postValue(repository.getAllNotificationAlarm())
        return _notificationAlarm
    }

    /**
     * NotificationAlarmEntity
     * AnimeWorkEntity
     * をinsertします。
     */
    fun insertNotificationAlarm(
        notificatioId: Int, animeId: Int, animeTitle: String,
        dayOfWeek: Int, hour: Int, minute: Int, second: Int,
        beforeSecond: Int, beforeTimeText: String
    ) {
        repository.insertNotificationAlarm(notificatioId, animeId, beforeSecond, beforeTimeText)
        repository.insertAnimeWork(animeId, animeTitle, dayOfWeek, hour, minute, second)
    }

    /**
     * NotificationAlarmEntity
     * をdeleteします。
     */
    fun deleteNotificatioAlarm(notificatioId: Int) {
        repository.deleteAniemWorkById(notificatioId)
    }
}
