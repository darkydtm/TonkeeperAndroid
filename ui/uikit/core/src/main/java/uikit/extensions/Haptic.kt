package uikit.extensions

import android.view.View
import uikit.HapticHelper
import uikit.HapticType

fun View.haptic(type: HapticType): Boolean {
	if (!isEnabled || !isAttachedToWindow) {
		return false
	}

	return HapticHelper.perform(context, type)
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
