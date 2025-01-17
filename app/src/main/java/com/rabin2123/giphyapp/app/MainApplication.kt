package com.rabin2123.giphyapp.app

import android.app.Application
import com.rabin2123.giphyapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
class MainApplication: Application(), KoinStartup {


    override fun onKoinStartup() = koinConfiguration {
        androidLogger(Level.DEBUG)
        androidContext(this@MainApplication)
        modules(listOf(appModule))
    }
}