package com.example.ehu.animeckecker

data class MyNotificationRow(
    val id: List<Int>,
    val animeId: Int,
    val animeTitle: String,
    val dayOfWeek: Int,
    val dayOfWeekText: String,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val startAtText: String,
    val time: Map<Int, String>
)