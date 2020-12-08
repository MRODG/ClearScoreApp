package com.mariosodigie.apps.clearscoreapp.dashboardfeature

import android.os.Bundle
import com.mariosodigie.apps.clearscoreapp.BaseActivity
import com.mariosodigie.apps.clearscoreapp.R
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.LightScoreReport
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.mariosodigie.apps.clearscoreapp.extensions.observe


class DashboardActivity : BaseActivity() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewModel.requestInProgress.observe(this) { requestInProgress ->
            if (requestInProgress) {
                buttonRefresh.showProgress()
            } else {
                buttonRefresh.hideProgress()
            }
        }

        viewModel.lightScoreReport.observe(this){scoreReport ->
            updateScoreIndicatorView(scoreReport)
        }

        addErrorSource(viewModel.serviceError)

        buttonRefresh.setOnClickListener{
            viewModel.updateScoreIndicatorData()
        }
    }

    private fun updateScoreIndicatorView(lightScoreReport: LightScoreReport){
        scoreIndicatorView.showScore(lightScoreReport)
    }
}