package com.rabin2123.domain.models

data class GifsInfoModel(
    val gifList: List<GifItem>,
    val status: Int,
    val offset: Int,
) {
    data class GifItem(
        val id: String,
        val smallPicUrl: String,
        val fullPicUrl: String,
        val title: String
    )
}