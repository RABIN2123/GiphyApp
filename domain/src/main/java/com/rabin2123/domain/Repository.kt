package com.rabin2123.domain

import androidx.paging.PagingData
import com.rabin2123.data.remote.models.GiphyApiResponse
import com.rabin2123.domain.models.GifsInfoModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun pagerGifList(): Flow<PagingData<GifsInfoModel.GifItem>>
    suspend fun searchGifList(title: String, page: Int): GifsInfoModel
    suspend fun hideGif(id: String)
}