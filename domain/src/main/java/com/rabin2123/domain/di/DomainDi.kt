package com.rabin2123.domain.di

import com.rabin2123.data.di.dataModule
import com.rabin2123.domain.Repository
import com.rabin2123.domain.RepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule
    get() = module {
        includes(dataModule)
        factoryOf(::RepositoryImpl) { bind<Repository>() }
    }