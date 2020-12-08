package com.mariosodigie.apps.clearscoreapp.dashboardfeature.api

import com.mariosodigie.apps.clearscoreapp.BuildConfig
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.LightScoreReport
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.ResponseData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("endpoint.json")
    fun requestLightScoreReport(): Call<ResponseData>

    companion object {
        fun getBaseURL(): String {
            return BuildConfig.DASHBOARD_FEATURE_BASE_URL
        }
    }
}