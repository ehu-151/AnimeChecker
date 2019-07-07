package com.example.ehu.animeckecker

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreferences(context: Context) {
    private val APP = "anime_checker"

    // data
    private val ACCESS_TOKEN = "access_token"

    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(APP, Context.MODE_PRIVATE)
    }


    fun setToken(token: String) {
        preferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getToken(): String {
        return preferences.getString(ACCESS_TOKEN, "")
    }
}