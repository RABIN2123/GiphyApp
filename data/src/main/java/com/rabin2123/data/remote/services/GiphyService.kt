package com.rabin2123.data.remote.services

import com.rabin2123.data.remote.RetrofitBuilder
import com.rabin2123.data.remote.models.GiphyApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GiphyService {
    @GET("trending?api_key=${RetrofitBuilder.API_KEY}")
    suspend fun getGifs(
        @Query("offset") page: Int,
        @Query("limit") limit: Int
    ): GiphyApiResponse

    @GET("search?api_key=${RetrofitBuilder.API_KEY}")
    suspend fun searchGifs(
        @Query("q") title: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): GiphyApiResponse
}