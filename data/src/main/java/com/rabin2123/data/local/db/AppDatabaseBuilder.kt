package com.rabin2123.data.local.db

import android.content.Context
import androidx.room.Room

internal object AppDatabaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null
    fun getDatabaseCacheGifList(context: Context) = INSTANCE ?: synchronized(this) {
        val instance = Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "database.db"
            )
            .build()
        INSTANCE = instance
        instance
    }
}