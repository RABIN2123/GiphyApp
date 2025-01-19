package com.rabin2123.data.di

import com.rabin2123.data.CombinedDataRepository
import com.rabin2123.data.CombinedDataRepositoryImpl
import com.rabin2123.data.local.db.AppDatabase
import com.rabin2123.data.local.db.cachegifsdb.CacheGifListDao
import com.rabin2123.data.local.db.AppDatabaseBuilder
import com.rabin2123.data.local.db.helper.CacheGifListHelper
import com.rabin2123.data.local.db.helper.CacheGifListHelperImpl
import com.rabin2123.data.paging.GifsRemoteMediator
import com.rabin2123.data.remote.ApiHelper
import com.rabin2123.data.remote.ApiHelperImpl
import com.rabin2123.data.remote.RetrofitBuilder
import com.rabin2123.data.remote.services.GiphyService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule
    get() = module {
        single<AppDatabase> {
            AppDatabaseBuilder.getDatabaseCacheGifList(get())
        }

        single<CacheGifListDao> { get<AppDatabase>().getCacheDao }
        singleOf(::CacheGifListHelperImpl) { bind<CacheGifListHelper>() }

        single<GiphyService> { RetrofitBuilder.giphyService }
        singleOf(::ApiHelperImpl) { bind<ApiHelper>() }

        singleOf(::GifsRemoteMediator)

        singleOf(::CombinedDataRepositoryImpl) { bind<CombinedDataRepository>() }
    }