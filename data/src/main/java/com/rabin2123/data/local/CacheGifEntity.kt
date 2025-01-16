package com.rabin2123.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CacheGifEntity.TABLE_NAME)
data class CacheGifEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_URL) val picUrl: String,
    @ColumnInfo(name = COLUMN_NAME_GIF) val nameGif: String
) {
    companion object {
        const val TABLE_NAME = "cache_info_gif_list"
        const val COLUMN_URL = "pic_url"
        const val COLUMN_NAME_GIF = "name_gif"
    }
}
