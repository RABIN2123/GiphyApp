package com.rabin2123.domain.di

import com.rabin2123.data.di.dataModule
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)
}