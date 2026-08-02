package uikit.widget

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.PathInterpolator
import androidx.core.animation.doOnEnd

internal fun calculatePredictiveBackTranslation(
	containerHeight: Int,
	contentTop: Int,
	progress: Float,
): Float {
	val distance = (containerHeight - contentTop).coerceAtLeast(0)
	return distance * progress.coerceIn(0f, 1f)
}

internal class VerticalPredictiveBackController(
	private val container: View,
	private val content: View,
	private val onProgress: (Float) -> Unit,
	private val onClose: () -> Unit,
) {
	private var animator: ValueAnimator? = null
	private var progress = 0f
	private var active = false
	private var closing = false

	fun start() {
		content.animate().cancel()
		cancelAnimator()
		active = true
		closing = false
		applyProgress(0f)
	}

	fun update(progress: Float) {
		if (!active || container.height == 0 || content.height == 0) {
			return
		}

		applyProgress(progress)
	}

	fun cancel() {
		if (!active || closing) {
			return
		}

		animateTo(0f, close = false)
	}

	fun complete() {
		if (active && !closing) {
			cancel()
		}
	}

	fun close(): Boolean {
		if (!active) {
			return false
		}
		if (!closing) {
			animateTo(1f, close = true)
		}
		return true
	}

	fun dispose() {
		cancelAnimator()
		active = false
		closing = false
		progress = 0f
		content.translationY = 0f
	}

	private fun applyProgress(value: Float) {
		progress = value.coerceIn(0f, 1f)
		content.translationY = calculatePredictiveBackTranslation(
			containerHeight = container.height,
			contentTop = content.top,
			progress = progress,
		)
		onProgress(progress)
	}

	private fun animateTo(target: Float, close: Boolean) {
		cancelAnimator()
		closing = close
		animator = ValueAnimator.ofFloat(progress, target).apply {
			duration = animationDuration
			interpolator = VerticalPredictiveBackController.interpolator
			addUpdateListener { applyProgress(it.animatedValue as Float) }
			doOnEnd {
				animator = null
				active = false
				closing = false
				if (close) {
					onClose()
				}
			}
			start()
		}
	}

	private fun cancelAnimator() {
		animator?.apply {
			removeAllListeners()
			removeAllUpdateListeners()
			cancel()
		}
		animator = null
	}

	private companion object {
		private const val animationDuration = 225L
		private val interpolator = PathInterpolator(.2f, 0f, 0f, 1f)
	}
}
