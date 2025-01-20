package com.rabin2123.data.local.db.helper

import androidx.paging.PagingSource
import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity
import com.rabin2123.data.local.db.cachegifsdb.CacheGifListDao

internal class CacheGifListHelperImpl(private val dao: CacheGifListDao) : CacheGifListHelper {

    override fun pagerGifs(): PagingSource<Int, CacheGifEntity> {
        return dao.pagingSource()
    }

    override fun searchPagerGifs(title: String): PagingSource<Int, CacheGifEntity> {
        return dao.searchPagingSource("%$title%")
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