package com.rabin2123.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity
import com.rabin2123.data.local.db.AppDatabase
import com.rabin2123.data.local.db.remotekeys.RemoteKeysEntity
import com.rabin2123.data.remote.ApiHelper
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
internal class GifsRemoteMediator(
    private val appDb: AppDatabase,
    private val gifsApi: ApiHelper,
) :
    RemoteMediator<Int, CacheGifEntity>() {
    var title: String = ""
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CacheGifEntity>
    ): MediatorResult {

        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }

            else -> {
                pageKeyData as Int
            }
        }
        try {
            val response = if (title.isEmpty()) {
                gifsApi.getGifs(
                    page = page,
                    limit = state.config.pageSize
                )
            } else {
                gifsApi.getSearchedGifs(
                    title = title,
                    page = page,
                    limit = state.config.pageSize
                )
            }

            val isEndOfList = response.data.isEmpty()
            appDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDb.getRemoveKeyDao.clearRemoteKeys()
                    appDb.getCacheDao.deleteAllGifList()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - PAGE_SIZE
                val nextKey = if (isEndOfList) null else page + PAGE_SIZE
                val keys = response.data.map {
                    RemoteKeysEntity(
                        id = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                appDb.getRemoveKeyDao.insertAll(keys)
                appDb.getCacheDao.insertAllGifList(response.data.map { it.toEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }

    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, CacheGifEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(PAGE_SIZE) ?: DEFAULT_PAGE_INDEX
            }

            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                remoteKeys?.nextKey ?: DEFAULT_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }


    private suspend fun getClosestRemoteKey(state: PagingState<Int, CacheGifEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { gifId ->
                appDb.getRemoveKeyDao.remoteKeysId(gifId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, CacheGifEntity>): RemoteKeysEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { gif -> appDb.getRemoveKeyDao.remoteKeysId(gif.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, CacheGifEntity>): RemoteKeysEntity? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { gif -> appDb.getRemoveKeyDao.remoteKeysId(gif.id) }
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 0
        const val PAGE_SIZE = 10
    }
}