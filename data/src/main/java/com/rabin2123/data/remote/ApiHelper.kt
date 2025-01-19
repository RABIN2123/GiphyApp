package com.rabin2123.data.remote

import com.rabin2123.data.remote.models.GiphyApiResponse

interface ApiHelper {
    suspend fun getGifs(page: Int, limit: Int): GiphyApiResponse
    suspend fun getSearchedGifs(title: String, page: Int, limit: Int): GiphyApiResponse
}