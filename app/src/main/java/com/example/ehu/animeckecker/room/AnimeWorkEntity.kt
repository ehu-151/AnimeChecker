package com.example.ehu.animeckecker.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 可能な限り変数名はGET /v1/worksに近づける
 */
@Entity
data class AnimeWorkEntity(
    @PrimaryKey
    val id: Int,
    val Title: String,
    val dayOfWeek: Int,
    val dayOfWeekText: String,
    val hour: Int,
    val minute: Int,
    val second: Int
)