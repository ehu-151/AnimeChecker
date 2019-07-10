package com.example.ehu.animeckecker.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = MostRecentAnimeEpisodeEntity::class,
            parentColumns = arrayOf("animeId"),
            childColumns = arrayOf("aniimeId")
        ),
        ForeignKey(
            entity = MostRecentAnimeEpisodeEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("aniimeId")
        )
    )
)
data class NotificationAlarmEntity(
    @PrimaryKey
    val id: Int,
    val animeId: Int,
    val beforeSecond: Int,
    val beforeTimeText: String
)