package com.rabin2123.data

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rabin2123.data.local.db.helper.CacheGifListHelper
import com.rabin2123.data.local.db.cachegifsdb.CacheGifEntity
import com.rabin2123.data.paging.GifsRemoteMediator
import kotlinx.coroutines.flow.Flow

internal class CombinedDataRepositoryImpl(
    private val localDb: CacheGifListHelper,
    private val pagerMediator: GifsRemoteMediator,
    private val context: Context
) : CombinedDataRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun pagerGifList(title: String): Flow<PagingData<CacheGifEntity>> {
        pagerMediator.title = title
        return Pager(
            config = PagingConfig(pageSize = GifsRemoteMediator.PAGE_SIZE),
            remoteMediator = pagerMediator,
            pagingSourceFactory = {
                if (isNetworkAvailable() || title.isEmpty()) {
                    localDb.pagerGifs()
                } else {
                    localDb.searchPagerGifs(title)
                }
            }
        ).flow
    }

    override suspend fun hideGif(id: String) {
        localDb.hideGif(id)
    }

    @SuppressWarnings("MissingPermission")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                    actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED) &&
                    (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) -> true

            else -> false
        }
    }
}