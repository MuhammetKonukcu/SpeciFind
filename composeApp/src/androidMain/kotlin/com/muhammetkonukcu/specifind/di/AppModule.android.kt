package com.muhammetkonukcu.specifind.di

import com.muhammetkonukcu.specifind.database.getDatabase
import com.muhammetkonukcu.specifind.room.database.AppDatabase
import org.koin.dsl.module

actual fun databaseModule() = module {
    single<AppDatabase> { getDatabase(get()) }
}