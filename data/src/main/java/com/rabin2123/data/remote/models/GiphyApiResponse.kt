package com.rabin2123.data.remote.models

import com.google.gson.annotations.SerializedName

data class GiphyApiResponse(
    @SerializedName("data")
    val data: List<DataObject>,
    @SerializedName("meta")
    val meta: Status
) {
    data class DataObject(
        @SerializedName("id")
        val id: String,
        @SerializedName("images")
        val images: Images
    )
    data class Images(
        @SerializedName("fixed_width_downsampled")
        val smallImage: Url,
        @SerializedName("fixed_height")
        val fixedHeight: Url
    )
    data class Url(
        @SerializedName("url")
        val url: String
    )
    data class Status(
        @SerializedName("status")
        val status: Int
    )
}