package com.rabin2123.data.remote

import com.rabin2123.data.remote.models.GiphyApiResponse
import com.rabin2123.data.remote.services.GiphyService

internal class ApiHelperImpl(private val giphyService: GiphyService): ApiHelper {
    override suspend fun getGifs(page: Int, limit: Int): GiphyApiResponse {
        return giphyService.getGifs(page, limit)
    }

    override suspend fun getSearchedGifs(title: String, page: Int, limit: Int): GiphyApiResponse {
        return giphyService.searchGifs(title, page, limit)
    }

}