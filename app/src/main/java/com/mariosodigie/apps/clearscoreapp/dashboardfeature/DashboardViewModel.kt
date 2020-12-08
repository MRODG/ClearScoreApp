package com.mariosodigie.apps.clearscoreapp.dashboardfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mariosodigie.apps.clearscoreapp.BaseViewModel
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.LightScoreReport
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.ScoreRating
import com.mariosodigie.apps.clearscoreapp.utils.OpenForTesting

@OpenForTesting
class DashboardViewModel(private val service: DashboardService, context: Context): BaseViewModel(context){

    init {
        updateScoreIndicatorData()
    }

    val lightScoreReport = MutableLiveData<LightScoreReport>()

    final fun updateScoreIndicatorData(){
        service.getLightScoreReport(ServiceCallbackImpl { data ->
            calculateScoreRating(data)
        })
    }

    private fun calculateScoreRating(scoreReport: LightScoreReport) {
        scoreReport.scoreRating = getScoreRating(scoreReport.scoreBand)
        scoreReport.ratingFraction = scoreReport.score.toFloat() / scoreReport.maxScoreValue
        lightScoreReport.value = scoreReport
    }

    private fun getScoreRating(scoreBand: Int): ScoreRating{
        return when(scoreBand){
            0 -> ScoreRating.VeryPoor
            1 -> ScoreRating.Poor
            2 -> ScoreRating.Fair
            3 -> ScoreRating.Good
            4 -> ScoreRating.Excellent
            else -> ScoreRating.Undefined
        }
    }

}