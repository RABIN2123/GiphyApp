package com.rabin2123.giphyapp.di

import com.rabin2123.domain.di.domainModule
import org.koin.dsl.module

val appModule = module {
    includes(domainModule)
}