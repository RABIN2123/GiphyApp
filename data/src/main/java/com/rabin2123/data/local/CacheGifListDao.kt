package com.rabin2123.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheGifListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>)

    @Query("SELECT * FROM ${CacheGifEntity.TABLE_NAME}")
    suspend fun getAllGifList(): List<CacheGifEntity>

    @Query("SELECT * FROM ${CacheGifEntity.TABLE_NAME} WHERE ${CacheGifEntity.COLUMN_TITLE} LIKE :searchTitle")
    suspend fun searchLocalGif(searchTitle: String): List<CacheGifEntity>

    @Query("DELETE FROM ${CacheGifEntity.TABLE_NAME}")
    suspend fun deleteAllGifList()
}