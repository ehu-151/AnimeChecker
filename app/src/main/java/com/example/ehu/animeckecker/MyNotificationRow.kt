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
) : Serializable {

    // ディープコピー
    fun copyRow(
        id: MutableList<Int> = this.id,
        animeId: Int = this.animeId,
        animeTitle: String = this.animeTitle,
        dayOfWeek: Int? = this.dayOfWeek,
        dayOfWeekText: String? = this.dayOfWeekText,
        hour: Int? = this.hour,
        minute: Int? = this.minute,
        second: Int? = this.second,
        startAtText: String? = this.startAtText,
        time: MutableMap<Int, String> = this.time
    ) = MyNotificationRow(
        id.toMutableList(),
        animeId,
        animeTitle,
        dayOfWeek,
        dayOfWeekText,
        hour,
        minute,
        second,
        startAtText,
        time.toMutableMap()
    )

}