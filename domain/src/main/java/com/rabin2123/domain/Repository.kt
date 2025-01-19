package com.rabin2123.domain

import androidx.paging.PagingData
import com.rabin2123.domain.models.GifsInfoModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun pagerGifList(title: String): Flow<PagingData<GifsInfoModel.GifItem>>
    suspend fun hideGif(id: String)
}