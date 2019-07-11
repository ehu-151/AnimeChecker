package com.example.ehu.animeckecker.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = arrayOf(
//        ForeignKey(
//            entity = AnimeWorkEntity::class,
//            parentColumns = arrayOf("id"),
//            childColumns = arrayOf("animeId")
//        )
//    )
)
data class NotificationAlarmEntity(
    @PrimaryKey
    val id: Int,
    val animeId: Int,
    val beforeSecond: Int,
    val beforeTimeText: String
)