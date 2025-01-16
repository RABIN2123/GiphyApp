package com.rabin2123.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheGifListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>)

    @Query("SELECT * FROM ${CacheGifEntity.TABLE_NAME}")
    fun getAllGifList(): Flow<List<CacheGifEntity>>

    @Query("DELETE FROM ${CacheGifEntity.TABLE_NAME}")
    suspend fun deleteAllGifList()
}