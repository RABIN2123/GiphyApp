package com.rabin2123.data.local

import kotlinx.coroutines.flow.Flow

internal class CacheGifListHelperImpl(private val dao: CacheGifListDao) : CacheGifListHelper {
    override suspend fun getAllGifList(): List<CacheGifEntity> {
        return dao.getAllGifList()
    }

    override suspend fun searchGifList(title: String): List<CacheGifEntity> {
        return dao.searchLocalGif(title)
    }

    override suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>) {
        dao.insertAllGifList(cacheGifList)
    }

    override suspend fun deleteAll() {
        dao.deleteAllGifList()
    }
}