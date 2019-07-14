package com.example.ehu.animeckecker.repository

import com.example.ehu.animeckecker.remote.AcceseTokenModel
import com.example.ehu.animeckecker.remote.LoginSevice
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class LoginRepository {
    //Retrofitインターフェース
    private var servise: LoginSevice

    init {
        //okhttpのclient作成
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        //クライアント生成

        var retrofit = Retrofit.Builder()
            .baseUrl(LoginSevice.baseUri)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        servise = retrofit.create(LoginSevice::class.java)
    }

    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
        code: String
    ): Response<AcceseTokenModel> {
        val response = servise.getAcceseToken(
            clientId = clientId,
            client_secret = clientSecret,
            redirectUri = redirectUrl,
            code = code
        ).execute()
        if (response == null) {
            throw Exception("responseがnull")
        } else {
            return response
        }
    }

    fun logout(accessToken: String) {
        servise.revokeToken(accessToken)
    }
}