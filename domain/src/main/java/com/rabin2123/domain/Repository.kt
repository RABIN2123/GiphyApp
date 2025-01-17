package com.rabin2123.domain

import com.rabin2123.data.remote.models.GiphyApiResponse
import com.rabin2123.domain.models.GifsInfoModel

interface Repository {
    suspend fun getGifList(page: Int): GifsInfoModel
    suspend fun searchGifList(title: String, page: Int): GifsInfoModel
    suspend fun insertCacheGifList(gifListInfo: GiphyApiResponse)
    fun deleteGif()
}