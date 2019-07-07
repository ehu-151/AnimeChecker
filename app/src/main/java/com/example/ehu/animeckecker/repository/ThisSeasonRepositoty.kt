package com.example.ehu.animeckecker.repository

import com.example.ehu.animeckecker.remote.*
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ThisSeasonRepositoty() {
    private var servise: AnnictWorksServise

    init {
        //okhttpのclient作成
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val type = Types.newParameterizedType(
            ImageList::class.java,
            Facebook::class.java,
            Twitter::class.java
        )


        //クライアント生成

        var retrofit = Retrofit.Builder()
            .baseUrl(AnnictWorksServise.baseUri)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        servise = retrofit.create(AnnictWorksServise::class.java)
    }

    fun getWorks(filterSeason: String, accessToken: String): Response<AnnictWorksModel> {
        val response = servise.getWorks(filterSeason, accessToken).execute()
        if (response == null) {
            throw Exception("responseがnull")
        } else {
            return response
        }
    }
}