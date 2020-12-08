package com.mariosodigie.apps.clearscoreapp.dashboardfeature.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mariosodigie.apps.clearscoreapp.R
import com.mariosodigie.apps.clearscoreapp.dashboardfeature.model.LightScoreReport
import com.mariosodigie.apps.clearscoreapp.extensions.formatString
import kotlinx.android.synthetic.main.score_indicator_inner_layout.*


class ScoreIndicatorView @JvmOverloads constructor(context: Context,
                                                   attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = 0
)    : FrameLayout(context, attrs, defStyleAttr){

    private val scoreTextView: TextView
    private val maxScoreTextView: TextView

    private val indicatorBounds = resources.getDimension(R.dimen.circle_diameter_1)
    private val startAnglePoint = 270f
    private val rect = RectF(20f, 20f, indicatorBounds - 20f, indicatorBounds - 20f)

    private val  paint: Paint = Paint()

    private var angle: Float

    init {
        descendantFocusability = FOCUS_BLOCK_DESCENDANTS

        val linearLayout = inflate(context, R.layout.score_indicator_inner_layout, null) as LinearLayout
        linearLayout.isClickable = false

        //Chose not to use android synthetic here in order to make the code more readable and understand where views are coming from
        scoreTextView = linearLayout.findViewById(R.id.scoreText)
        maxScoreTextView = linearLayout.findViewById(R.id.maxScoreText)
        maxScoreTextView.formatString(R.string.max_score_line, 0)

        val borderDrawable: Drawable = context.getDrawable(R.drawable.score_indicator_border)!!
        val indicatorView = ImageView(context)
        indicatorView.setImageDrawable(borderDrawable)

        angle = 0f

        setWillNotDraw(false)

        addView(indicatorView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER))
        addView(linearLayout)

    }

    fun showScore(scoreReport: LightScoreReport) {

        maxScoreTextView.formatString(R.string.max_score_line, scoreReport.maxScoreValue)
        buildIndicatorPaint(scoreReport.scoreRating.colour)

        scoreTextView.apply {
            text = scoreReport.score.toString()
            setTextColor(resources.getColor(scoreReport.scoreRating.colour, null))
        }

        //Calculating the angle of the score indicator based on the score faction
        val newAngle = 360f * scoreReport.ratingFraction


        ValueAnimator.ofFloat(angle, newAngle).apply {

            duration = resources.getInteger(R.integer.animation_long).toLong()
            addUpdateListener {
                angle = it.animatedValue as Float
                requestLayout()
                invalidate()
            }
        }.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawArc(rect, startAnglePoint, angle, false, paint)
    }

    private fun buildIndicatorPaint(scoreColor: Int){
        paint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 8f
            color = resources.getColor(scoreColor, null)
        }
    }

}