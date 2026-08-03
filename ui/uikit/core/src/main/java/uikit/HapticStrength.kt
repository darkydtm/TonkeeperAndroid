package uikit

import kotlin.math.roundToInt

object HapticStrength {

	const val MIN = 0f
	const val MAX = 3f
	const val DEFAULT = 1f
	const val STEP = 0.1f

	fun normalize(value: Float): Float {
		if (!value.isFinite()) {
			return DEFAULT
		}
		return ((value.coerceIn(MIN, MAX) / STEP).roundToInt() * STEP)
			.coerceIn(MIN, MAX)
	}

	fun scaleAmplitude(amplitude: Int, strength: Float): Int {
		return (amplitude * normalize(strength)).roundToInt().coerceIn(0, 255)
	}
}
