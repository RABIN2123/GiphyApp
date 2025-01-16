package com.rabin2123.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CacheGifEntity::class], version = 1, exportSchema = false)
abstract class CacheGifListDatabase: RoomDatabase() {
    abstract val dao: CacheGifListDao
}