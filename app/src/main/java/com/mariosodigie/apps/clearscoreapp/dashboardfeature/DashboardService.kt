package com.mariosodigie.apps.clearscoreapp.dashboardfeature

import com.mariosodigie.apps.clearscoreapp.api.ApiError
import com.mariosodigie.apps.clearscoreapp.api.ServiceCallback
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.api.ApiService
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.LightScoreReport
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.ResponseData
import com.mariosodigie.apps.clearscoreapp.utils.ConnectivityCheck
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardService(private val apiServe: ApiService,
                       private val connectivityCheck: ConnectivityCheck){

    fun getLightScoreReport(callback: ServiceCallback<LightScoreReport>){

        if (!connectivityCheck.isConnectedToNetwork()) {
            callback.onError(ApiError.PhoneOffline)
            return
        }

        apiServe.requestLightScoreReport().enqueue(object: Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback.onError(t)
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if(response.isSuccessful){
                    callback.onSuccess(response.body()!!.lightScoreReport)
                }
                else{
                    if(response.code()==404) callback.onError(ApiError.CreditFileNotFound)
                    else callback.onError(Throwable(response.message()))
                }
            }
        })
    }
}