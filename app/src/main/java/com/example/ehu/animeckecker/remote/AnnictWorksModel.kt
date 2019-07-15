package com.example.ehu.animeckecker.remote

import com.squareup.moshi.Json

data class AnnictWorksModel(
    val works: MutableList<Works>,
    @field:Json(name = "total_count")
    val totalCount: Int,
    @field:Json(name = "next_page")
    val nextPage: Int?,
    @field:Json(name = "prev_page")
    val prev_page: String?
)

data class Works(
    val id: Int,
    val title: String,
    @field:Json(name = "title_kana")
    val titleKana: String,
    val media: String,
    @field:Json(name = "media_text")
    val mediaText: String,
    @field:Json(name = "season_name")
    val seasonName: String,
    @field:Json(name = "season_name_text")
    val seasonNameText: String,
    @field:Json(name = "released_on")
    val releasedOn: String,
    @field:Json(name = "released_on_about")
    val releasedOnAbout: String,
    @field:Json(name = "official_site_url")
    val officialSiteUrl: String,
    @field:Json(name = "wikipedia_url")
    val wikipediaUrl: String,
    @field:Json(name = "twitter_username")
    val twitterUsername: String,
    @field:Json(name = "twitter_hashtag")
    val twitterHashtag: String,
    @field:Json(name = "syobocal_tid")
    val syobocalTid: String,
    @field:Json(name = "mal_anime_id")
    val malAnimeId: String,
    val images: ImageList,
    @field:Json(name = "episodes_count")
    val episodesCount: Int,
    @field:Json(name = "watchers_count")
    val watchersCount: Int,
    @field:Json(name = "reviews_count")
    val reviewsCount: Int,
    @field:Json(name = "no_episodes")
    val noEpisodes: Boolean
)

data class ImageList(
    @field:Json(name = "recommended_url")
    val recommendedUrl: String,
    val facebook: Facebook,
    val twitter: Twitter
)

data class Facebook(
    @field:Json(name = "og_image_url")
    val ogImageUrl: String
)

data class Twitter(
    @field:Json(name = "mini_avatar_url")
    val miniAvatarUrl: String,
    @field:Json(name = "normal_avatar_url")
    val normalAvatarUrl: String,
    @field:Json(name = "bigger_avatar_url")
    val biggerAvatarUrl: String,
    @field:Json(name = "original_avatar_url")
    val originalAvatarUrl: String,
    @field:Json(name = "image_url")
    val imageUrl: String
)