package com.example.ehu.animeckecker.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SubscriptionEntity(
    @PrimaryKey
    val animeId: Int,
    val animeTitle: String,
    val startAt: Date,
    val notifyAt: List<Date>
)