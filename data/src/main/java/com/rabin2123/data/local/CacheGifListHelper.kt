package com.rabin2123.data.local

import androidx.paging.PagingSource
import com.rabin2123.data.local.cachegifsdb.CacheGifEntity

interface CacheGifListHelper {
    fun pagerGifs(): PagingSource<Int, CacheGifEntity>
    suspend fun searchGifList(title: String): List<CacheGifEntity>
    suspend fun hideGif(id: String)
    suspend fun insertAllGifList(cacheGifList: List<CacheGifEntity>)
    suspend fun deleteAll()
}