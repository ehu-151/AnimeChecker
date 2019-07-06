package com.example.ehu.animeckecker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Query(value = "SELECT * FROM subscriptionentity")
    fun getAll(): List<SubscriptionEntity>

    @Insert
    fun insert(entity: SubscriptionEntity)
}