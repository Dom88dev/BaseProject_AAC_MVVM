package com.project.base.di


import com.project.base.model.remote.api.WeatherAPI
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * @author Leopold
 */
val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(WeatherAPI::class.java) }
}