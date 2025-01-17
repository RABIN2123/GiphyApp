package com.rabin2123.data.di

import com.rabin2123.data.local.CacheGifListDao
import com.rabin2123.data.local.CacheGifListDatabaseBuilder
import com.rabin2123.data.local.CacheGifListHelper
import com.rabin2123.data.local.CacheGifListHelperImpl
import com.rabin2123.data.remote.ApiHelper
import com.rabin2123.data.remote.ApiHelperImpl
import com.rabin2123.data.remote.RetrofitBuilder
import com.rabin2123.data.remote.services.GiphyService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule
    get() = module {
        single<CacheGifListDao> {
            CacheGifListDatabaseBuilder.getDatabaseCacheGifList(get()).dao
        }
        singleOf(::CacheGifListHelperImpl) { bind<CacheGifListHelper>() }

        single<GiphyService> {
            RetrofitBuilder.giphyService
        }
        singleOf(::ApiHelperImpl) { bind<ApiHelper>() }
    }