package com.example.ehu.animeckecker.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 可能な限り変数名はGET /v1/worksに近づける
 */
@Entity
data class ImmutableAnimeWorkEntity(
    @PrimaryKey
    val id: Int,
    val Title: String
)