package com.rabin2123.data.remote

import com.rabin2123.data.remote.models.GiphyApiResponse

interface ApiHelper {
    suspend fun getGifList(page: Int): GiphyApiResponse
    suspend fun searchGifList(title: String, page: Int): GiphyApiResponse
}