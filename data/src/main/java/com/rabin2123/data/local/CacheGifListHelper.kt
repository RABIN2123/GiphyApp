package com.rabin2123.data.local

import kotlinx.coroutines.flow.Flow

interface CacheGifListHelper {
    fun getAllGifList(): Flow<List<CacheGifEntity>>
    suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>)
    suspend fun deleteAll()
}