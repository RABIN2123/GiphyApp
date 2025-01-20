package com.rabin2123.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity
import com.rabin2123.data.local.db.cachegifsdb.CacheGifListDao
import com.rabin2123.data.local.db.remotekeys.RemoteKeysDao
import com.rabin2123.data.local.db.remotekeys.RemoteKeysEntity

@Database(entities = [CacheGifEntity::class, RemoteKeysEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val getCacheDao: CacheGifListDao
    abstract val getRemoveKeyDao: RemoteKeysDao
}