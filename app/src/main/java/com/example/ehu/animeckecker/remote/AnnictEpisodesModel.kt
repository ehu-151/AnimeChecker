package com.example.ehu.animeckecker.remote

import com.squareup.moshi.Json

data class AnnictEpisodesModel(
    val id: Int,
    val number: Int,
    @field:Json(name = "number_text")
    val numberText: String,
    @field:Json(name = "sort_number")
    val sortNumber: Int,
    val title: String,
    @field:Json(name = "records_count")
    val recordsCount: Int,
    @field:Json(name = "record_comments_count")
    val recordCommentsCount: Int,
    val work: Works,
    @field:Json(name = "prev_episode")
    val prevEpisode: AnnictEpisodesModel,
    @field:Json(name = "next_episode")
    val nextEpisode: AnnictEpisodesModel
)