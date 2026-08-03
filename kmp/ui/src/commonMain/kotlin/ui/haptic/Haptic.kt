package ui.haptic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.hapticfeedback.LocalHapticFeedback
import androidx.compose.ui.semantics.Role

enum class HapticType {
	LIGHT,
	SELECTION,
	CONFIRM,
	SUCCESS,
	WARNING,
	ERROR,
}

@Composable
fun rememberHapticClick(
	type: HapticType = HapticType.LIGHT,
	onClick: () -> Unit,
): () -> Unit {
	val haptic = LocalHapticFeedback.current
	val currentOnClick = rememberUpdatedState(onClick)
	return remember(haptic, type) {
		{
			haptic.performHapticFeedback(type.toComposeType())
			currentOnClick.value()
		}
	}
}

fun Modifier.hapticClickable(
	enabled: Boolean = true,
	type: HapticType = HapticType.LIGHT,
	role: Role? = null,
	onClick: () -> Unit,
): Modifier {
	return composed {
		val haptic = LocalHapticFeedback.current
		val currentOnClick = rememberUpdatedState(onClick)
		clickable(enabled = enabled, role = role) {
			haptic.performHapticFeedback(type.toComposeType())
			currentOnClick.value()
		}
	}
}

fun Modifier.hapticSelectable(
	selected: Boolean,
	enabled: Boolean = true,
	type: HapticType = HapticType.SELECTION,
	role: Role? = null,
	onClick: () -> Unit,
): Modifier {
	return composed {
		val haptic = LocalHapticFeedback.current
		val currentOnClick = rememberUpdatedState(onClick)
		selectable(selected = selected, enabled = enabled, role = role) {
			if (!selected) {
				haptic.performHapticFeedback(type.toComposeType())
			}
			currentOnClick.value()
		}
	}
}

private fun HapticType.toComposeType(): HapticFeedbackType = when (this) {
	HapticType.LIGHT -> HapticFeedbackType.ContextClick
	HapticType.SELECTION -> HapticFeedbackType.ClockTick
	HapticType.CONFIRM,
	HapticType.SUCCESS -> HapticFeedbackType.Confirm
	HapticType.WARNING,
	HapticType.ERROR -> HapticFeedbackType.Reject
}
