package com.rabin2123.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rabin2123.data.local.db.helper.CacheGifListHelper
import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity
import com.rabin2123.data.paging.GifsRemoteMediator
import kotlinx.coroutines.flow.Flow

internal class CombinedDataRepositoryImpl(
    private val localDb: CacheGifListHelper,
    private val pagerMediator: GifsRemoteMediator
) : CombinedDataRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun pagerGifList(title: String): Flow<PagingData<CacheGifEntity>> {
        pagerMediator.title = title
        return Pager(
            config = PagingConfig(pageSize = GifsRemoteMediator.PAGE_SIZE),
            remoteMediator = pagerMediator,
            pagingSourceFactory = { localDb.pagerGifs() }
        ).flow
    }

    override suspend fun hideGif(id: String) {
        localDb.hideGif(id)
    }
}