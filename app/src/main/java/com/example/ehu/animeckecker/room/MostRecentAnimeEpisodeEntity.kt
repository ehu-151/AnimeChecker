package com.example.ehu.animeckecker.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MostRecentAnimeEpisodeEntity(
    @PrimaryKey
    val animeId: Int,
    val dayOfWeek: Int,
    val dayOfWeekText: String,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val episodeNumberText: String?,
    val episodeTitle: String?

)