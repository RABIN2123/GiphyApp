package com.rabin2123.data.remote

import com.rabin2123.data.remote.models.GiphyApiResponse
import com.rabin2123.data.remote.services.GiphyService

internal class ApiHelperImpl(private val giphyService: GiphyService): ApiHelper {
    override suspend fun getGifList(page: Int): GiphyApiResponse {
        return giphyService.getGifs(page = page)
    }

    override suspend fun searchGifList(title: String, page: Int): GiphyApiResponse {
        return giphyService.searchGifs(title, page)
    }
}