package com.muhammetkonukcu.specifind

import android.app.Application
import com.muhammetkonukcu.specifind.di.initKoin
import org.koin.android.ext.koin.androidContext

class SpeciFindApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SpeciFindApp)
        }
    }
}