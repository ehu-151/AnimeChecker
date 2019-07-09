package com.example.ehu.animeckecker.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SubscriptionEntity(
    @PrimaryKey
    val id: Int,
    val animeId: Int,
    val animeTitle: String,
    val dayOfWeek: Int,
    val dayOfWeekText: String,
    val startAt: Calendar,
    val startAtText: String,
    val beforeSecond: Int,
    val beforeTimeText: String,

    val episodeNumberText: String?,
    val episodeTitle: String?
)