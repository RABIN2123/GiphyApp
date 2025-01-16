package com.rabin2123.data.remote

import com.rabin2123.data.remote.models.GiphyApiResponse
import com.rabin2123.data.remote.services.GiphyService

class ApiHelperImpl(private val giphyService: GiphyService): ApiHelper {
    override suspend fun getGifList(page: Int, limit: Int): GiphyApiResponse {
        return giphyService.getGifs(page = page, limit = limit)
    }
}