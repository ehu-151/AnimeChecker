package com.example.ehu.animeckecker.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.ehu.animeckecker.room.AnimeWorkEntity
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
            getDao().insertNotificationAlarm(entity)
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

    fun insertAnimeWork(
        id: Int, title: String, dayOfWeek: Int,
        hour: Int, minute: Int, second: Int
    ) {
        GlobalScope.launch {
            val entity = AnimeWorkEntity(
                id, title, dayOfWeek,
                hour, minute, second
            )
            getDao().insertAnimeWork(entity)
        }
    }

    fun getAllAniemWork(): List<AnimeWorkEntity> {
        var result: List<AnimeWorkEntity> = mutableListOf()
        runBlocking {
            thread {
                result = getDao().getAllAnimeWork()
                Log.d("db_info_get", result.toString())
            }
        }
        return result
    }

    fun deleteAniemWorkById(notificatioId: Int) {
        runBlocking {
            thread {
                getDao().deleteAnimeWorkById(notificatioId)
            }
        }
    }


    private fun getDao(): DatabaseDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
            .getDao()
    }

}