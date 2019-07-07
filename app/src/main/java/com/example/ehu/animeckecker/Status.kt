package com.example.ehu.animeckecker

sealed class Status<out T> {
    object Logging : Status<Nothing>()
    data class Success<T>(val data: T) : Status<T>()
    data class Failure(val throwable: Throwable) : Status<Nothing>()
}