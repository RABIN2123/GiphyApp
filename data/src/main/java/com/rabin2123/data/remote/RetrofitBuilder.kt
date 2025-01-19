package com.rabin2123.data.remote

import com.rabin2123.data.remote.services.GiphyService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitBuilder {
    private const val BASE_URL = "https://api.giphy.com/v1/gifs/"
    internal const val API_KEY = "miIxt3Gl7YaZErQR3DRlAGRuRDjZHruk"

    private fun getRetrofit() =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    val giphyService: GiphyService = getRetrofit().create(GiphyService::class.java)
}