package com.rabin2123.data.remote.services

import com.rabin2123.data.remote.models.GiphyApiResponse
import retrofit2.http.Query

interface GiphyService {
    suspend fun getGifs(
        @Query("offset") page: Int,
        @Query("limit") limit: Int
    ): GiphyApiResponse
}