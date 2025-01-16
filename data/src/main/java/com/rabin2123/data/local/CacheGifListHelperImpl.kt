package com.rabin2123.data.local

import kotlinx.coroutines.flow.Flow

class CacheGifListHelperImpl(private val dao: CacheGifListDao) : CacheGifListHelper {
    override fun getAllGifList(): Flow<List<CacheGifEntity>> {
        return dao.getAllGifList()
    }

    override suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>) {
        dao.insertAllGifList(cacheGifList)
    }

    override suspend fun deleteAll() {
        dao.deleteAllGifList()
    }
}