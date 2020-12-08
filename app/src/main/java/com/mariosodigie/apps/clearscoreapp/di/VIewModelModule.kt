package com.mariosodigie.apps.clearscoreapp.di

import com.mariosodigie.apps.clearscoreapp.dashboardfeature.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {DashboardViewModel(get(), get())}
}