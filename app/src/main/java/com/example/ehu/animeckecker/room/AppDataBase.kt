package com.example.ehu.animeckecker.room

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.text.SimpleDateFormat
import java.util.*

@Database(entities = arrayOf(NotificationAlarmEntity::class, AnimeWorkEntity::class), version = 1)
@TypeConverters(CalenderConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "anime-database-0.03"

    }

    // DAOを取得する。
    abstract fun getDao(): DatabaseDao
}


class CalenderConverter {
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")

    @TypeConverter
    fun toCalender(str: String): Calendar {
        val c = Calendar.getInstance()
        val date = sdf.parse(str)
        c.setTime(date)
        return c
    }

    @TypeConverter
    fun fromCalender(cal: Calendar): String {
        val str = sdf.format(cal.getTime())
        return str
    }
}