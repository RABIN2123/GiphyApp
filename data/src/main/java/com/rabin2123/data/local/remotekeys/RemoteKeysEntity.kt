package com.rabin2123.data.local.remotekeys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RemoteKeysEntity.TABLE_NAME)
data class RemoteKeysEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID) val id: String,
    @ColumnInfo(name = COLUMN_PREV_KEY) val prevKey: Int?,
    @ColumnInfo(name = COLUMN_NEXT_KEY) val nextKey: Int?
) {
    companion object {
        const val TABLE_NAME = "remote_keys"
        const val COLUMN_ID = "id"
        const val COLUMN_PREV_KEY = "prev_key"
        const val COLUMN_NEXT_KEY = "next_key"
    }
}