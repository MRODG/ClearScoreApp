package com.mariosodigie.apps.clearscoreapp

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.DashboardActivity
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.DashboardViewModel
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.LightScoreReport
import com.mariosodigie.apps.clearscoreapp.test.ViewModelRule
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4::class)
class DashboardActivityTest {

    @get:Rule
    val testRule = IntentsTestRule(DashboardActivity::class.java, true, false)

    @get:Rule
    val viewModelRule = ViewModelRule()

    private val lightScoreReport = MutableLiveData<LightScoreReport>()
    private val requestInProgress = MutableLiveData<Boolean>()
    private val serviceError = MutableLiveData<ServiceError>()

    @Mock
    lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        whenever(viewModel.lightScoreReport).thenReturn(lightScoreReport)
        whenever(viewModel.requestInProgress).thenReturn(requestInProgress)
        whenever(viewModel.serviceError).thenReturn(serviceError)
        testRule.launchActivity(null)
    }

    @Test
    fun displaysScore() {
        val scoreReport = LightScoreReport(500, 700, 4)
        lightScoreReport.postValue(scoreReport)
        onView(withId(R.id.scoreIndicatorView)).check(matches(isDisplayed()))
        onView(withId(R.id.scoreText)).check(matches(isDisplayed()))
        onView(withId(R.id.maxScoreText)).check(matches(isDisplayed()))
        onView(withId(R.id.scoreInnerLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonRefresh)).check(matches(isDisplayed()))
        onView(withText("500")).check(matches(isDisplayed()))
    }

    @Test
    fun displaysProgressButton() {
        onView(withId(R.id.buttonRefresh)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonText)).check(matches(isDisplayed()))
        onView(withText(500))
    }
}