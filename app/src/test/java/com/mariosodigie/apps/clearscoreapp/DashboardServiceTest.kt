package com.mariosodigie.apps.clearscoreapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mariosodigie.apps.clearscoreapp.api.ApiError
import com.mariosodigie.apps.clearscoreapp.api.ServiceCallback
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.DashboardService
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.api.ApiService
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.LightScoreReport
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.ResponseData
import com.mariosodigie.apps.clearscoreapp.utils.ConnectivityCheck
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardServiceTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var connectivityCheck: ConnectivityCheck

    @Mock
    lateinit var apiServe: ApiService

    private lateinit var service: DashboardService

    @Before
    fun setUp() {
        service = DashboardService(apiServe, connectivityCheck)
    }

    @Test
    fun lightScoreReportIsSuccessfullyRequested() {

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(true)

        val callback = mock<ServiceCallback<LightScoreReport>>()
        val serviceCall = mock<Call<ResponseData>>()
        whenever(apiServe.requestLightScoreReport()).thenReturn(serviceCall)

        val lightScoreReport = LightScoreReport(500, 700, 4)
        val responseData = mock<ResponseData>()
        whenever(responseData.lightScoreReport).thenReturn(lightScoreReport)

        val response = mock<Response<ResponseData>>()
        whenever(response.isSuccessful).thenReturn(true)
        whenever(response.body()).thenReturn(responseData)

        doAnswer {
            it.getArgument<Callback<ResponseData>>(0).onResponse(serviceCall,response)
        }.whenever(serviceCall).enqueue(any())

        service.getLightScoreReport(callback)

        verify(callback).onSuccess(lightScoreReport)
    }

    @Test
    fun lightScoreReportFailsWhenPhoneIsOffline() {

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(false)

        val callback = mock<ServiceCallback<LightScoreReport>>()
        val serviceCall = mock<Call<ResponseData>>()
        whenever(apiServe.requestLightScoreReport()).thenReturn(serviceCall)

        val lightScoreReport = LightScoreReport(500, 700, 4)
        val responseData = mock<ResponseData>()
        whenever(responseData.lightScoreReport).thenReturn(lightScoreReport)

        val response = mock<Response<ResponseData>>()
        whenever(response.isSuccessful).thenReturn(true)
        whenever(response.body()).thenReturn(responseData)

        doAnswer {
            it.getArgument<Callback<ResponseData>>(0).onResponse(serviceCall,response)
        }.whenever(serviceCall).enqueue(any())

        service.getLightScoreReport(callback)

        verify(callback).onError(ApiError.PhoneOffline)
    }

    @Test
    fun lightScoreReportRequestedFilesWithThrowable() {

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(true)

        val callback = mock<ServiceCallback<LightScoreReport>>()
        val serviceCall = mock<Call<ResponseData>>()
        whenever(apiServe.requestLightScoreReport()).thenReturn(serviceCall)

        val lightScoreReport = LightScoreReport(500, 700, 4)
        val responseData = mock<ResponseData>()
        whenever(responseData.lightScoreReport).thenReturn(lightScoreReport)

        val throwable = Throwable("Failure")

        doAnswer {
            it.getArgument<Callback<ResponseData>>(0).onFailure(serviceCall,throwable)
        }.whenever(serviceCall).enqueue(any())

        service.getLightScoreReport(callback)

        verify(callback).onError(throwable)
    }

    @Test
    fun lightScoreReportRequestedIsNotSuccessfulWith404() {

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(true)

        val callback = mock<ServiceCallback<LightScoreReport>>()
        val serviceCall = mock<Call<ResponseData>>()
        whenever(apiServe.requestLightScoreReport()).thenReturn(serviceCall)

        val lightScoreReport = LightScoreReport(500, 700, 4)
        val responseData = mock<ResponseData>()
        whenever(responseData.lightScoreReport).thenReturn(lightScoreReport)

        val response = mock<Response<ResponseData>>()
        whenever(response.isSuccessful).thenReturn(false)
        whenever(response.code()).thenReturn(404)

        doAnswer {
            it.getArgument<Callback<ResponseData>>(0).onResponse(serviceCall,response)
        }.whenever(serviceCall).enqueue(any())

        service.getLightScoreReport(callback)

        verify(callback).onError(ApiError.CreditFileNotFound)
    }

    @Test
    fun lightScoreReportRequestedIsNotSuccessfulWith403() {

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(true)

        val callback = mock<ServiceCallback<LightScoreReport>>()
        val serviceCall = mock<Call<ResponseData>>()
        whenever(apiServe.requestLightScoreReport()).thenReturn(serviceCall)

        val lightScoreReport = LightScoreReport(500, 700, 4)
        val responseData = mock<ResponseData>()
        whenever(responseData.lightScoreReport).thenReturn(lightScoreReport)

        val response = mock<Response<ResponseData>>()
        whenever(response.isSuccessful).thenReturn(false)
        whenever(response.code()).thenReturn(403)
        whenever(response.message()).thenReturn("Forbidden")

        doAnswer {
            it.getArgument<Callback<ResponseData>>(0).onResponse(serviceCall,response)
        }.whenever(serviceCall).enqueue(any())

        service.getLightScoreReport(callback)

        verify(callback).onError(Throwable("Forbidden"))
    }
}
