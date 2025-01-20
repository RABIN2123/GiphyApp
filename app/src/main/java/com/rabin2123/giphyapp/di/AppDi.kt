package com.rabin2123.giphyapp.di

import com.rabin2123.domain.di.domainModule
import com.rabin2123.giphyapp.presentation.ListOfGifsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule
    get() = module {
        includes(domainModule)
        viewModel {
            ListOfGifsViewModel(
                repository = get()
            )
        }
    }