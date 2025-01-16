package com.rabin2123.data.local

import android.content.Context
import androidx.room.Room

object CacheGifListDatabaseBuilder {
    @Volatile
    private var INSTANCE: CacheGifListDatabase? = null
    fun getDatabaseCacheGifList(context: Context) = INSTANCE ?: synchronized(this) {
        val instance = Room
            .databaseBuilder(
                context,
                CacheGifListDatabase::class.java,
                "database.db")
            .build()
        INSTANCE = instance
        instance
    }
}