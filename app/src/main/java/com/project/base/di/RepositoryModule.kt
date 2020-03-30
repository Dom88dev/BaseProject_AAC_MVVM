package com.project.base.di

import com.project.base.repository.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherRepository(get(), get()) }
}