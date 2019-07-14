package com.example.ehu.animeckecker

import java.io.Serializable

data class MyNotificationRow(
    val id: List<Int>? = null,
    val animeId: Int,
    val animeTitle: String,
    val dayOfWeek: Int? = null,
    val dayOfWeekText: String? = null,
    val hour: Int? = null,
    val minute: Int? = null,
    val second: Int? = null,
    val startAtText: String? = null,
    val time: Map<Int, String>? = null
) : Serializable