package com.tonapps.tonkeeper.ui.screen.settings.theme.picker

import android.graphics.Color
import com.tonapps.wallet.data.core.MaterialYouHexColor

data class ColorPickerState(
	val hue: Float,
	val saturation: Float,
	val value: Float,
) {
	val color: Int
		get() = Color.HSVToColor(floatArrayOf(hue, saturation, value))

	val hex: String
		get() = MaterialYouHexColor.format(color)

	fun withHue(y: Float, height: Float): ColorPickerState {
		if (height <= 0f) {
			return this
		}
		return copy(hue = (y.coerceIn(0f, height) / height * 360f).coerceAtMost(359.999f))
	}

	fun withSaturationValue(
		x: Float,
		y: Float,
		width: Float,
		height: Float,
	): ColorPickerState {
		if (width <= 0f || height <= 0f) {
			return this
		}
		return copy(
			saturation = (x / width).coerceIn(0f, 1f),
			value = 1f - (y / height).coerceIn(0f, 1f),
		)
	}

	companion object {
		fun fromColor(color: Int): ColorPickerState {
			val hsv = FloatArray(3)
			Color.colorToHSV(color, hsv)
			return ColorPickerState(
				hue = hsv[0],
				saturation = hsv[1],
				value = hsv[2],
			)
		}

		fun fromHex(value: String): ColorPickerState? {
			return MaterialYouHexColor.parse(value)?.let(::fromColor)
		}
	}
}
