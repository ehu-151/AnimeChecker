package com.example.ehu.animeckecker.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.ehu.animeckecker.room.AppDatabase
import com.example.ehu.animeckecker.room.DatabaseDao
import com.example.ehu.animeckecker.room.NotificationAlarmEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class NorificationAlarmRepository(private val context: Context) {

    fun insertNotificationAlarm(
        notificatioId: Int,
        animeId: Int,
        beforeSecond: Int,
        beforeTimeText: String
    ) {
        GlobalScope.launch {
            val entity = NotificationAlarmEntity(
                notificatioId, animeId, beforeSecond, beforeTimeText
            )
            val result = getDao().insertNotificationAlarm(entity)
            Log.d("db_info_insert", result.toString())

        }
    }

    fun getAllNotificationAlarm(): List<NotificationAlarmEntity> {
        var result: List<NotificationAlarmEntity> = mutableListOf()
        runBlocking {
            thread {
                result = getDao().getAllNotificationAlarm()
                Log.d("db_info_get", result.toString())

            }
        }
        return result
    }


    private fun getDao(): DatabaseDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
            .getDao()
    }

}