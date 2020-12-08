package com.mariosodigie.apps.clearscoreapp.dashboardfeature.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.mariosodigie.apps.clearscoreapp.R

enum class ScoreRating(@StringRes val title: Int, @ColorRes val colour: Int) {

    VeryPoor(R.string.score_very_poor_name, R.color.score_very_poor),
    Poor(R.string.score_poor_name, R.color.score_poor),
    Fair(R.string.score_fair_name, R.color.score_fair),
    Good(R.string.score_good_name, R.color.score_good),
    Excellent(R.string.score_good_excellent_name, R.color.score_excellent),
    Undefined(R.string.score_undefined_name, R.color.score_undefined)
}