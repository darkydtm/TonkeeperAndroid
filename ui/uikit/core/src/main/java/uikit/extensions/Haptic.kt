package uikit.extensions

import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import uikit.HapticType

fun View.haptic(type: HapticType): Boolean {
	if (!isEnabled || !isAttachedToWindow) {
		return false
	}

	return performHapticFeedback(type.feedbackConstant(Build.VERSION.SDK_INT))
}

fun View.setHapticClickListener(
	type: HapticType = HapticType.LIGHT,
	block: (View) -> Unit,
) {
	setOnClickListener { view ->
		view.haptic(type)
		block(view)
	}
}

internal fun HapticType.feedbackConstant(sdkInt: Int): Int = when (this) {
	HapticType.LIGHT -> HapticFeedbackConstants.KEYBOARD_TAP
	HapticType.SELECTION -> HapticFeedbackConstants.CLOCK_TICK
	HapticType.CONFIRM,
	HapticType.SUCCESS -> if (sdkInt >= Build.VERSION_CODES.R) {
		HapticFeedbackConstants.CONFIRM
	} else {
		HapticFeedbackConstants.CONTEXT_CLICK
	}
	HapticType.WARNING,
	HapticType.ERROR -> if (sdkInt >= Build.VERSION_CODES.R) {
		HapticFeedbackConstants.REJECT
	} else {
		HapticFeedbackConstants.LONG_PRESS
	}
}
