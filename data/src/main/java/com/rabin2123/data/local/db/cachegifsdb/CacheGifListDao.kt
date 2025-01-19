package com.rabin2123.data.local.db.cachegifsdb

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheGifListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>)

    @Query("UPDATE ${CacheGifEntity.TABLE_NAME} SET ${CacheGifEntity.COLUMN_VISIBLE} = 0 WHERE ${CacheGifEntity.COLUMN_NAME_ID} = :id")
    suspend fun hideGif(id: String)

    @Query("SELECT * FROM ${CacheGifEntity.TABLE_NAME} WHERE ${CacheGifEntity.COLUMN_VISIBLE} = 1")
    fun pagingSource(): PagingSource<Int, CacheGifEntity>

    @Query("DELETE FROM ${CacheGifEntity.TABLE_NAME} WHERE ${CacheGifEntity.COLUMN_VISIBLE} = 1")
    suspend fun deleteAllGifList()
}