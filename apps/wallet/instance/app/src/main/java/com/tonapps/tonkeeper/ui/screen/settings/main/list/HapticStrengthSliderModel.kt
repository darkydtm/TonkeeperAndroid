package com.tonapps.tonkeeper.ui.screen.settings.main.list

import uikit.HapticStrength
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign

class HapticStrengthSliderModel(initialValue: Float = HapticStrength.DEFAULT) {

	private var step = valueToStep(initialValue)
	private var dragRemainder = 0f

	val value: Float
		get() = step * HapticStrength.STEP

	fun setValue(value: Float) {
		step = valueToStep(value)
		dragRemainder = 0f
	}

	fun dragBySteps(distance: Float): Float {
		dragRemainder += distance
		while (step in MIN_STEP..MAX_STEP && dragRemainder != 0f) {
			val direction = dragRemainder.sign.roundToInt()
			if (direction < 0 && step == MIN_STEP || direction > 0 && step == MAX_STEP) {
				dragRemainder = 0f
				break
			}
			val threshold = if (step % DETENT_STEP == 0) DETENT_RESISTANCE else 1f
			if (abs(dragRemainder) < threshold) {
				break
			}
			step += direction
			dragRemainder -= direction * threshold
		}
		return value
	}

	private fun valueToStep(value: Float): Int {
		return (HapticStrength.normalize(value) / HapticStrength.STEP)
			.roundToInt()
			.coerceIn(MIN_STEP, MAX_STEP)
	}

	private companion object {
		const val MIN_STEP = 0
		const val MAX_STEP = 30
		const val DETENT_STEP = 5
		const val DETENT_RESISTANCE = 1.5f
	}
}

fun formatHapticStrengthPercent(value: Float): String {
	return "${(HapticStrength.normalize(value) * 100).roundToInt()}%"
}
