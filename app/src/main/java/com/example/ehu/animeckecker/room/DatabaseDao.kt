package com.example.ehu.animeckecker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Query(value = "SELECT * FROM notificationalarmentity")
    fun getAll(): List<NotificationAlarmEntity>

    @Insert
    fun insert(entity: NotificationAlarmEntity)
}