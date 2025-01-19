package com.rabin2123.data.local.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM ${RemoteKeysEntity.TABLE_NAME} WHERE ${RemoteKeysEntity.COLUMN_ID} = :id")
    suspend fun remoteKeysDoggoId(id: String): RemoteKeysEntity?

    @Query("DELETE FROM ${RemoteKeysEntity.TABLE_NAME}")
    suspend fun clearRemoteKeys()

}