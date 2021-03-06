package com.example.ehu.animeckecker.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.ehu.animeckecker.room.AnimeWorkEntity
import com.example.ehu.animeckecker.room.AppDatabase
import com.example.ehu.animeckecker.room.DatabaseDao
import com.example.ehu.animeckecker.room.NotificationAlarmEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotificationAlarmRepository(private val context: Context) {

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
            Log.d("app_db_insert", entity.toString())
        }
    }

    fun getAllNotificationAlarm(): List<NotificationAlarmEntity> {
        var result: List<NotificationAlarmEntity> = mutableListOf()
        runBlocking {
            GlobalScope.async {
                result = getDao().getAllNotificationAlarm()
                Log.d("app_db_get", result.toString())
            }.await()
        }

        return result
    }

    fun getAllNotificationAlarmByAnimeId(animeId: Int): List<NotificationAlarmEntity> {
        var result: List<NotificationAlarmEntity> = mutableListOf()
        runBlocking {
            GlobalScope.async {
                result = getDao().getAllNotificationAlarmByAnimeId(animeId)
                Log.d("app_db_get", result.toString())
            }.await()
        }

        return result
    }

    fun deleteNotificationAlarmById(notificatioId: Int) {
        GlobalScope.launch {
            getDao().deleteNotificationAlarmByid(notificatioId)
            Log.d("app_db_delete", notificatioId.toString())
        }
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
            Log.d("app_db_insert", entity.toString())
        }
    }

    fun getAniemWorkById(animeId: Int): List<AnimeWorkEntity> {
        var result: List<AnimeWorkEntity> = mutableListOf()
        runBlocking {
            GlobalScope.async {
                result = getDao().getAllAnimeWorkById(animeId)
                Log.d("app_db_get", result.toString())
            }.await()
        }
        return result
    }


    fun deleteAniemWorkById(id: Int) {
        GlobalScope.launch {
            getDao().deleteAnimeWorkById(id)
            Log.d("app_db_delete", id.toString())
        }
    }


    private fun getDao(): DatabaseDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
            .getDao()
    }

}