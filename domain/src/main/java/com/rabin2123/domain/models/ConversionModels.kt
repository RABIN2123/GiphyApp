package com.rabin2123.domain.models

import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity

fun CacheGifEntity.toDomain():GifsInfoModel.GifItem {
    return GifsInfoModel.GifItem(
        id = this.id,
        smallPicUrl = this.urlSmallPic,
        fullPicUrl = this.urlFullPic,
        title = this.title
    )
}
