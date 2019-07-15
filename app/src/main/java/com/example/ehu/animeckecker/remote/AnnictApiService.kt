package com.example.ehu.animeckecker.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnnictApiService {
    companion object {
        const val baseUri = "https://api.annict.com/"
    }


    @GET("/v1/works")
    fun getWorks(
        @Query("access_token") accessToken: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 25,
        @Query("filter_season") filterSeason: String?
    ): Call<AnnictWorksModel>

    @GET("/v1/episodes")
    fun getEpisodes(
        @Query("access_token") accessToken: String,
        @Query("filter_work_id") filterWorkId: Int?,
        @Query("page") page: Int?,
        @Query("sort_id") sortId: String?
    ): Call<AnnictEpisodesModel>

//    @GET("/v1/me/programs")
//    fun getMePrograms(
//        @Query("access_token") accessToken: String
//    ): Call<AnnictEpisodesModel>
}