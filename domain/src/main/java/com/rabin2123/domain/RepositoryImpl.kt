package com.rabin2123.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rabin2123.data.local.cachegifsdb.CacheGifEntity
import com.rabin2123.data.local.CacheGifListHelper
import com.rabin2123.data.paging.GifsRemoteMediator
import com.rabin2123.data.remote.ApiHelper
import com.rabin2123.data.remote.models.GiphyApiResponse
import com.rabin2123.domain.models.GifsInfoModel
import com.rabin2123.domain.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(private val remote: ApiHelper,private val local: CacheGifListHelper, private val remoteMediator: GifsRemoteMediator) : Repository {

    @OptIn(ExperimentalPagingApi::class)
    override fun pagerGifList(): Flow<PagingData<GifsInfoModel.GifItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { local.pagerGifs() }
        ).flow.map { it.map { item-> item.toDomain() } }
    }

    override suspend fun searchGifList(title: String, page: Int): GifsInfoModel {
        return try {
            remote.searchGifList(title, page).toDomain()
        } catch (ex: Exception) {
            local.searchGifList(title).toDomain()
        }
    }


    override suspend fun hideGif(id: String) {
        local.hideGif(id)
    }
}