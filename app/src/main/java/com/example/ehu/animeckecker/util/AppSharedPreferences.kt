package com.example.ehu.animeckecker.util

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreferences(context: Context) {
    private val APP = "anime_checker"
    private var preferences: SharedPreferences

    // data
    private val ACCESS_TOKEN = "access_token"
    private val IS_LOGIN = "is_login"

    init {
        preferences = context.getSharedPreferences(APP, Context.MODE_PRIVATE)
    }


    fun setToken(token: String) {
        preferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getToken(): String {
        return preferences.getString(ACCESS_TOKEN, "")
    }

    fun setIsLogin(token: Boolean) {
        preferences.edit().putBoolean(IS_LOGIN, token).apply()
    }

    fun getIsLogin(): Boolean {
        return preferences.getBoolean(IS_LOGIN, false)
    }
}