package com.rabin2123.data

import androidx.paging.PagingData
import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity
import kotlinx.coroutines.flow.Flow

interface CombinedDataRepository {
    fun pagerGifList(title: String): Flow<PagingData<CacheGifEntity>>
    suspend fun hideGif(id: String)
}