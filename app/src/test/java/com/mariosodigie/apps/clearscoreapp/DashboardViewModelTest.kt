package com.mariosodigie.apps.clearscoreapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mariosodigie.apps.clearscoreapp.api.ServiceCallback
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.DashboardService
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.DashboardViewModel
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.LightScoreReport
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.ResponseData
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.ScoreRating
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class DashboardViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var dashboardService: DashboardService

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        viewModel = DashboardViewModel(dashboardService, context)
    }


    @Test
    fun getsLightScoreReportFromViewModelInit() {
        verify(dashboardService).getLightScoreReport(any())
    }

    @Test
    fun signalsWhenRequestStarts() {
        val progressObserver = mock<Observer<Boolean>>()
        viewModel.requestInProgress.observeForever(progressObserver)

        verify(progressObserver).onChanged(true)
    }

    @Test
    fun getsLightScoreReportFromService() {
        viewModel.updateScoreIndicatorData()
        verify(dashboardService, times(2)).getLightScoreReport(any())
    }

    @Test
    fun signalsLightScoreReportIsWhenRequested() {
        val lightScoreReport = LightScoreReport(500, 700, 4)

        doAnswer {
            it.getArgument<ServiceCallback<LightScoreReport>>(0).onSuccess(lightScoreReport)
        }.whenever(dashboardService).getLightScoreReport(any())

        val observer = mock<Observer<LightScoreReport>>()
        viewModel.lightScoreReport.observeForever(observer)
        viewModel.updateScoreIndicatorData()
        verify(observer).onChanged(lightScoreReport)
    }

    @Test
    fun signalsLightScoreReportWithScoreRatingAndScoreFraction() {

        val lightScoreReport = LightScoreReport(500, 700, 4)
        val ratingFraction = 500.toFloat() / 700

        doAnswer {
            it.getArgument<ServiceCallback<LightScoreReport>>(0).onSuccess(lightScoreReport)
        }.whenever(dashboardService).getLightScoreReport(any())

        val observer = mock<Observer<LightScoreReport>>()
        viewModel.lightScoreReport.observeForever(observer)
        viewModel.updateScoreIndicatorData()

        assertEquals(lightScoreReport.ratingFraction, ratingFraction)
        assertEquals(lightScoreReport.scoreRating, ScoreRating.Excellent)
    }

    @Test
    fun signalsErrorWhenLightScoreReportFails() {
        val errorMessage = "failure"

        doAnswer {
            it.getArgument<ServiceCallback<String>>(0).onError(RuntimeException(errorMessage))
        }.whenever(dashboardService).getLightScoreReport(any())

        whenever(context.getString(any())).thenReturn(errorMessage)

        val errorObserver = mock<Observer<ServiceError>>()
        viewModel.serviceError.observeForever(errorObserver)

        viewModel.updateScoreIndicatorData()

        verify(errorObserver).onChanged(argThat {
            message == errorMessage
        })
    }

}