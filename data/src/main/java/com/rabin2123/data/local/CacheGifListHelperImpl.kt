package com.rabin2123.data.local

import androidx.paging.PagingSource
import com.rabin2123.data.local.cachegifsdb.CacheGifEntity
import com.rabin2123.data.local.cachegifsdb.CacheGifListDao

internal class CacheGifListHelperImpl(private val dao: CacheGifListDao) : CacheGifListHelper {

    override fun pagerGifs(): PagingSource<Int, CacheGifEntity> {
        return dao.pagingSource()
    }

    override suspend fun searchGifList(title: String): List<CacheGifEntity> {
        return dao.searchLocalGif(title)
    }

    override suspend fun hideGif(id: String) {
        dao.hideGif(id)
    }

    override suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>) {
        dao.insertAllGifList(cacheGifList)
    }

    override suspend fun deleteAll() {
        dao.deleteAllGifList()
    }
}