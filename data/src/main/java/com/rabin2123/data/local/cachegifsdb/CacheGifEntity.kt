package com.rabin2123.data.local.cachegifsdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CacheGifEntity.TABLE_NAME)
data class CacheGifEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_ID) val id: String,
    @ColumnInfo(name = COLUMN_URL_FULL_PIC) val urlFullPic: String,
    @ColumnInfo(name = COLUMN_URL_SMALL_PIC) val urlSmallPic: String,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_VISIBLE) val visible: Int
) {
    companion object {
        const val COLUMN_NAME_ID = "id"
        const val TABLE_NAME = "cache_info_gif_list"
        const val COLUMN_URL_FULL_PIC = "full_pic_url"
        const val COLUMN_URL_SMALL_PIC = "small_pic_url"
        const val COLUMN_TITLE = "title"
        const val COLUMN_VISIBLE = "visible"
    }
}
