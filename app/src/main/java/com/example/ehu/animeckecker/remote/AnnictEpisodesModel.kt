package com.example.ehu.animeckecker.remote

import com.squareup.moshi.Json

data class AnnictEpisodesModel(
    val episodes: List<Episodes>,
    @field:Json(name = "total_count")
    val totalCount:Int,
    @field:Json(name = "next_page")
    val nextPage:Int?,
    @field:Json(name = "prev_page")
    val prevPage:Int?
)

data class Episodes(
    val id: Int,
    val number: Int?,
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
    val prevEpisode: Episodes?,
    @field:Json(name = "next_episode")
    val nextEpisode: Episodes?
)