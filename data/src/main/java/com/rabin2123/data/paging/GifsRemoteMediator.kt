package com.rabin2123.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rabin2123.data.local.cachegifsdb.CacheGifEntity
import com.rabin2123.data.local.AppDatabase
import com.rabin2123.data.remote.services.GiphyService
import retrofit2.HttpException
import java.io.IOException

//TODO ДОБАВИТЬ НОРМАЛЬНУЮ ЛИСТАЛКУ

@OptIn(ExperimentalPagingApi::class)
class GifsRemoteMediator(private val gifsDb: AppDatabase, private val gifsApi: GiphyService) :
    RemoteMediator<Int, CacheGifEntity>() {
    private var pageInt = 0
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CacheGifEntity>
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> pageInt = 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        pageInt = 0
                    } else {
                        pageInt += 10
                    }
                }
            }

            val gifs = gifsApi.getGifs(
                page = pageInt,
                limit = state.config.pageSize
            )

            gifsDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gifsDb.getCacheDao.deleteAllGifList()
                }
                val gifsEntities = gifs.data.map { it.ToEntity() }
                gifsDb.getCacheDao.insertAllGifList(gifsEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = gifs.data.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}