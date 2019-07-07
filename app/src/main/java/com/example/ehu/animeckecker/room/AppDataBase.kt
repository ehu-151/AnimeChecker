package com.example.ehu.animeckecker.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
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

    // String(,で区切られている)
    // List<String>
    // List<Long>
    // List<Date>
    @TypeConverter
    fun fromString(value: String): List<Date> {
        val longList = value.split(",").map { it.toLong() }
        return longList.map { Date(it) }
    }

    // List<Date>
    // List<Long>
    // String(,で区切る)
    @TypeConverter
    fun listToString(list: List<Date>): String {
        val longList = list.map { it.time.toLong() }
        return longList.joinToString { "," }
    }
}