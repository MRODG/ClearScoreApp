package com.mariosodigie.apps.clearscoreapp.di

import com.mariosodigie.apps.clearscoreapp.dashboardfeature.DashboardService
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.api.ApiService
import com.mariosodigie.apps.clearscoreapp.utils.ConnectivityCheck
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single { ConnectivityCheck(get()) }
    single { DashboardService(get(), get()) }


    single {
        Retrofit
            .Builder()
            .baseUrl(ApiService.getBaseURL())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(ApiService::class.java)
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}