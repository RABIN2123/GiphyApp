package com.rabin2123.data.local

import kotlinx.coroutines.flow.Flow

interface CacheGifListHelper {
    suspend fun getAllGifList(): List<CacheGifEntity>
    suspend fun searchGifList(title: String): List<CacheGifEntity>
    suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>)
    suspend fun deleteAll()
}