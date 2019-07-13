package com.example.ehu.animeckecker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Query(value = "SELECT * FROM notificationalarmentity")
    fun getAllNotificationAlarm(): List<NotificationAlarmEntity>

    @Insert
    fun insertNotificationAlarm(entity: NotificationAlarmEntity)

    @Query("DELETE FROM notificationalarmentity WHERE id = :id")
    fun deleteNotificationAlarmByid(id: Int)

    @Query(value = "SELECT * FROM animeworkentity WHERE id =:animeId")
    fun getAllAnimeWorkById(animeId: Int): List<AnimeWorkEntity>

    @Insert
    fun insertAnimeWork(entity: AnimeWorkEntity)

    @Query("DELETE FROM AnimeWorkEntity WHERE id = :id")
    fun deleteAnimeWorkById(id: Int)

}