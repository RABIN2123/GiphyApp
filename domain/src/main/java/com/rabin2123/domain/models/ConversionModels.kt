package com.rabin2123.domain.models

import com.rabin2123.data.local.CacheGifEntity
import com.rabin2123.data.remote.models.GiphyApiResponse

fun GiphyApiResponse.toDomain(): GifsInfoModel {
    return GifsInfoModel(
        gifList = this.data.map { item ->
            GifsInfoModel.GifItem(
                id = item.id,
                smallPicUrl = item.images.smallImage.url,
                fullPicUrl = item.images.fixedHeight.url,
                title = item.title
            )
        },
        status = this.meta.status,
        offset = this.pagination.offset
    )
}

fun List<CacheGifEntity>.toDomain(): GifsInfoModel {
    return GifsInfoModel(
        gifList = this.map { item ->
            GifsInfoModel.GifItem(
                id = item.id,
                smallPicUrl = item.urlSmallPic,
                fullPicUrl = item.urlFullPic,
                title = item.title
            )
        },
        status = -1,
        offset = 0
    )
}

fun GiphyApiResponse.toEntity(): List<CacheGifEntity> {
    return this.data.map {item ->
        CacheGifEntity(
            id = item.id,
            urlFullPic = item.images.smallImage.url,
            urlSmallPic = item.images.fixedHeight.url,
            title = item.title,
        )
    }
}
