package com.rabin2123.data.paging

import com.rabin2123.data.local.cachegifsdb.CacheGifEntity
import com.rabin2123.data.remote.models.GiphyApiResponse

internal fun GiphyApiResponse.DataObject.ToEntity(): CacheGifEntity {
    return CacheGifEntity(
        id = this.id,
        urlFullPic = this.images.fixedHeight.url,
        urlSmallPic = this.images.smallImage.url,
        title = this.title,
        visible = 1
    )
}

internal fun CacheGifEntity.ToDataObject(): GiphyApiResponse.DataObject {
    return GiphyApiResponse.DataObject(
        id = this.id,
        images = GiphyApiResponse.Images(
            smallImage = GiphyApiResponse.Url(this.urlSmallPic),
            fixedHeight = GiphyApiResponse.Url(this.urlFullPic)
        ),
        title = this.title
    )
}