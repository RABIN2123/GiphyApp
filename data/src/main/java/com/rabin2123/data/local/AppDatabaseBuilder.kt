package com.rabin2123.data.local

import android.content.Context
import androidx.room.Room

object AppDatabaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null
    fun getDatabaseCacheGifList(context: Context) = INSTANCE ?: synchronized(this) {
        val instance = Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "database.db")
            .build()
        INSTANCE = instance
        instance
    }
}