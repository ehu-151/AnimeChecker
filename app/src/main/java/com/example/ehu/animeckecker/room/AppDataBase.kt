package com.example.ehu.animeckecker.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

@Database(entities = arrayOf(SubscriptionEntity::class), version = 1)
@TypeConverters(DateTimeConverter::class, ListDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "anime-database-0.1"

    }

    // DAOを取得する。
    abstract fun getDao(): DatabaseDao
}


class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

class ListDateConverter {
    @TypeConverter
    fun fromString(value: String): List<Date> {
        val listType = object : TypeToken<List<Date>>() {

        }.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<Date>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}