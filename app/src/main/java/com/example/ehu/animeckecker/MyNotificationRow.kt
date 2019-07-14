package com.example.ehu.animeckecker

import java.io.Serializable

data class MyNotificationRow(
    var id: MutableList<Int> = mutableListOf(),
    val animeId: Int,
    val animeTitle: String,
    var dayOfWeek: Int? = null,
    var dayOfWeekText: String? = null,
    var hour: Int? = null,
    var minute: Int? = null,
    var second: Int? = null,
    var startAtText: String? = null,
    var time: MutableMap<Int, String> = mutableMapOf()
) : Serializable