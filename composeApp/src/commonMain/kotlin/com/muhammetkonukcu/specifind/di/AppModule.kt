package com.muhammetkonukcu.specifind.di

import com.muhammetkonukcu.specifind.room.dao.HistoryDao
import com.muhammetkonukcu.specifind.room.database.AppDatabase
import com.muhammetkonukcu.specifind.room.repository.HistoryLocalRepository
import com.muhammetkonukcu.specifind.room.repository.HistoryLocalRepositoryImpl
import com.muhammetkonukcu.specifind.viewmodel.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun appModule(): Module = module {
    single<HomeViewModel> { HomeViewModel() }
}

expect fun databaseModule(): Module

fun localRepositoryModule(): Module = module {
    single<HistoryDao> { get<AppDatabase>().getHistoryDao() }
    single<HistoryLocalRepository> { HistoryLocalRepositoryImpl(get()) }
}

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)

        modules(
            appModule(),
            databaseModule(),
            localRepositoryModule()
        )
    }