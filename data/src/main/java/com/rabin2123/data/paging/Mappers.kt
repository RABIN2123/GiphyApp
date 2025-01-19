package com.rabin2123.data.paging

import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity
import com.rabin2123.data.remote.models.GiphyApiResponse

internal fun GiphyApiResponse.DataObject.toEntity(): CacheGifEntity {
    return CacheGifEntity(
        id = this.id,
        urlFullPic = this.images.fixedHeight.url,
        urlSmallPic = this.images.smallImage.url,
        title = this.title,
        visible = 1
    )
}