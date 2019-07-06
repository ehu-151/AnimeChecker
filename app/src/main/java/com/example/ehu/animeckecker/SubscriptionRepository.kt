package com.example.ehu.animeckecker

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.ehu.animeckecker.room.AppDatabase
import com.example.ehu.animeckecker.room.DatabaseDao
import com.example.ehu.animeckecker.room.SubscriptionEntity
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class SubscriptionRepository(private val context: Context) {

    fun setSubscription(entity: SubscriptionEntity) {
        runBlocking {
            thread {
                val result = getDao().insert(entity)
                Log.d("db_info_insert", result.toString())
            }
        }
    }

    fun getAllSubsctiption(): List<SubscriptionEntity> {
        var result: List<SubscriptionEntity> = mutableListOf()
        runBlocking {
            thread {
                result = getDao().getAll()
                Log.d("db_info_get", result.toString())

            }
        }
        return result
    }


    private fun getDao(): DatabaseDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
            .getDao()
    }

}