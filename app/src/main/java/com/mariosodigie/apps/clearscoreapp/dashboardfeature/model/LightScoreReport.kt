package com.mariosodigie.apps.clearscoreapp.dashboardfeature.model

import com.squareup.moshi.Json

data class ResponseData(
    @Json(name = "creditReportInfo") val lightScoreReport: LightScoreReport
)
data class LightScoreReport(
    @Json(name = "score")
    val score: Int,

    @Json(name = "maxScoreValue")
    val maxScoreValue: Int,

    @Json(name = "scoreBand")
    val scoreBand: Int,

    var scoreRating: ScoreRating = ScoreRating.Undefined,

    var ratingFraction: Float = 0f
)
