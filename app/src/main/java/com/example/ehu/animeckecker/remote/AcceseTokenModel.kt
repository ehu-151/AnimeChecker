package com.example.ehu.animeckecker.remote

import com.squareup.moshi.Json

class AcceseTokenModel(
    @field:Json(name = "access_token")
    val accessToken: String,
    @field:Json(name = "token_type")
    val tokenType: String,
    val scope: String,
    @field:Json(name = "created_at")
    val createdAt: String
)