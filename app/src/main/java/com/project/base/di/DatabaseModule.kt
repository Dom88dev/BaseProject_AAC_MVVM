package com.project.base.di

import com.project.base.model.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


/**
 *
 */
val roomModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single(createdAtStart = false) { get<AppDatabase>().weatherDao() }
}