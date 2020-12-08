package com.mariosodigie.apps.clearscoreapp.dashboardfeature.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.graphics.toRect
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.mariosodigie.apps.clearscoreapp.R


private val FADE_INTERPOLATOR = LinearInterpolator()

class ProgressButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var state = State.Text

    private val animator = ValueAnimator.ofFloat(1f, 0f).apply {
        duration = resources.getInteger(R.integer.animation_long).toLong()
        addUpdateListener {
            animationFraction = it.animatedValue as Float
            textView.alpha = 1f - animatedFraction
            updateDrawRect()
            invalidate()
        }
        doOnEnd {
            if (state == State.Text) {
                isActivated = false
                isEnabled = true
                textView.visibility = View.VISIBLE
                spinnerView.visibility = View.INVISIBLE
                spinnerView.cancelAnimation()
            } else {
                spinnerView.visibility = View.VISIBLE
                spinnerView.playAnimation()
                backgroundView.animate()
                    .alpha(0f)
                    .setInterpolator(FADE_INTERPOLATOR)
                    .setDuration(resources.getInteger(R.integer.animation_extra_short).toLong())
                    .start()
            }
        }
    }

    private var animationFraction = 1f

    private val backgroundRect = RectF()
    private val backgroundDrawable: Drawable = context.getDrawable(R.drawable.progress_button)!!

    private val spinnerView: LottieAnimationView
    private val backgroundView: ImageView
    private val textView: TextView

    init {
        isClickable = true
        descendantFocusability = FOCUS_BLOCK_DESCENDANTS

        textView = inflate(context, R.layout.label_progress, null) as TextView
        textView.isClickable = false

        val ta = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.text), defStyleAttr, 0)
        textView.text = ta.getText(0)
        ta.recycle()

        spinnerView = LottieAnimationView(context)
        spinnerView.setAnimation(R.raw.blue_spinner)
        spinnerView.repeatMode = LottieDrawable.RESTART
        spinnerView.repeatCount = ValueAnimator.INFINITE
        spinnerView.visibility = View.INVISIBLE
        spinnerView.setOnClickListener{
            hideProgress()
        }

        backgroundView = ImageView(context)
        backgroundView.setImageDrawable(backgroundDrawable)

        addView(spinnerView, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, Gravity.CENTER))
        addView(backgroundView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER))
        addView(textView)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        updateDrawRect()
    }

    private fun updateDrawRect() {
        val displayHeight = height.toFloat()
        val displayWidth = displayHeight + (width.toFloat() - displayHeight) * animationFraction
        backgroundRect.set((width - displayWidth) / 2, 0f, (width + displayWidth) / 2, displayHeight)
        backgroundDrawable.bounds = backgroundRect.toRect()
    }

    fun showProgress() {
        state = State.Progress
        isActivated = true
        isEnabled = false
        animator.start()
    }

    fun hideProgress() {
        state = State.Text
        textView.clearAnimation()

        backgroundView.animate()
            .alpha(1f)
            .setInterpolator(FADE_INTERPOLATOR)
            .setDuration(resources.getInteger(R.integer.animation_extra_short).toLong())
            .withEndAction {
                animator.reverse()
            }
            .start()
    }

    private enum class State {
        Text,
        Progress
    }
}
