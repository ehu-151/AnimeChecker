package com.example.ehu.animeckecker

data class MyNotificationRow(
    val id: Int,
    val animeId: Int,
    val animeTitle: String,
    val dayOfWeek: Int,
    val dayOdWeekText: String,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val startAtText: String,
    val time: Map<Int, String>
)