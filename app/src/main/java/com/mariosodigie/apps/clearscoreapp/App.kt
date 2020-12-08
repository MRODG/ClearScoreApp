package com.mariosodigie.apps.clearscoreapp

import android.app.Application
import com.mariosodigie.apps.clearscoreapp.di.appModule
import com.mariosodigie.apps.clearscoreapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }
    }
}