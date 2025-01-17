package com.rabin2123.domain

import com.rabin2123.data.local.CacheGifListHelper
import com.rabin2123.data.remote.ApiHelper
import com.rabin2123.data.remote.models.GiphyApiResponse
import com.rabin2123.domain.models.GifsInfoModel
import com.rabin2123.domain.models.toDomain
import com.rabin2123.domain.models.toEntity

class RepositoryImpl(private val remote: ApiHelper,private val local: CacheGifListHelper) : Repository {
    override suspend fun getGifList(page: Int): GifsInfoModel {
        return try {
            val result = remote.getGifList(page)
            insertCacheGifList(result)

            result.toDomain()
        } catch (ex: Exception) {
            local.getAllGifList().toDomain()
        }
    }

    override suspend fun searchGifList(title: String, page: Int): GifsInfoModel {
        return try {
            remote.searchGifList(title, page).toDomain()
        } catch (ex: Exception) {
            local.searchGifList(title).toDomain()
        }
    }

    override suspend fun insertCacheGifList(gifListInfo: GiphyApiResponse) {
        local.insertAllGifList(gifListInfo.toEntity())
    }

    override fun deleteGif() {
        TODO("Not yet implemented")
    }
}