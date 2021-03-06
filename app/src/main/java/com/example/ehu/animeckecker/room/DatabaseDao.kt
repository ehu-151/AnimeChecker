package com.example.ehu.animeckecker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Query(value = "SELECT * FROM notificationalarmentity")
    fun getAllNotificationAlarm(): List<NotificationAlarmEntity>

    @Query(value = "SELECT * FROM notificationalarmentity WHERE animeId=:animeId")
    fun getAllNotificationAlarmByAnimeId(animeId: Int): List<NotificationAlarmEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNotificationAlarm(entity: NotificationAlarmEntity)

    @Query("DELETE FROM notificationalarmentity WHERE id = :id")
    fun deleteNotificationAlarmByid(id: Int)

    @Query(value = "SELECT * FROM animeworkentity WHERE id =:animeId")
    fun getAllAnimeWorkById(animeId: Int): List<AnimeWorkEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAnimeWork(entity: AnimeWorkEntity)

    @Query("DELETE FROM AnimeWorkEntity WHERE id = :id")
    fun deleteAnimeWorkById(id: Int)

}