package com.rabin2123.data.local.db.helper

import androidx.paging.PagingSource
import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity

interface CacheGifListHelper {
    fun pagerGifs(): PagingSource<Int, CacheGifEntity>
    suspend fun hideGif(id: String)
    suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>)
    suspend fun deleteAll()
}