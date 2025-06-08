package com.muhammetkonukcu.specifind.di

import com.muhammetkonukcu.specifind.viewmodel.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun appModule(): Module = module {
    single<HomeViewModel> { HomeViewModel() }
}

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)

        modules(
            appModule()
        )
    }