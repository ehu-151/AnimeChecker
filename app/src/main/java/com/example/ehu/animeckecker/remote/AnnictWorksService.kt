package com.example.ehu.animeckecker.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnnictWorksService {
    companion object {
        const val baseUri = "https://api.annict.com/"
    }


    @GET("/v1/works")
    fun getWorks(
        @Query("filter_season") filterSeason: String?,
        @Query("access_token") accessToken: String
    ): Call<AnnictWorksModel>
}