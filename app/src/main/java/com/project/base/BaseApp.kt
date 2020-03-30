package com.project.base

import android.app.Application
import com.project.base.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //앱내에서 사용되어질 di의 모듈을 생성해준다.
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApp)
            modules(
                networkModule,
                apiModule,
                roomModule,
                repositoryModule,
                viewModelModule
            )
        }
    }

    companion object {
        //open.weather app의 api를 이요하기 위한 키
        const val WEATHER_API_KEY = "82b05521b26b350f40d09506274dcbe8"
    }
}