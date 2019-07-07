package com.example.ehu.animeckecker.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginSevise {
    companion object {
        const val baseUri = "https://api.annict.com/"
    }


    @POST("oauth/token")
    fun getAcceseToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") client_secret: String,
        @Query("grant_type") grantType: String = "authorization_code",
        @Query("redirect_uri") redirectUri: String,
        @Query("code") code: String
    ): Call<AcceseTokenModel>
}