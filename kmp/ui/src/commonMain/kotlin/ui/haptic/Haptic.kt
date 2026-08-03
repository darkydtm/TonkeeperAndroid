package ui.haptic

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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
fun rememberHaptic(type: HapticType = HapticType.LIGHT): () -> Unit {
	return rememberPlatformHaptic(type)
}

@Composable
fun rememberHapticClick(
	type: HapticType = HapticType.LIGHT,
	onClick: () -> Unit,
): () -> Unit {
	val haptic = rememberPlatformHaptic(type)
	val currentOnClick = rememberUpdatedState(onClick)
	return remember(haptic) {
		{
			haptic()
			currentOnClick.value()
		}
	}
}

fun Modifier.hapticClickable(
	enabled: Boolean = true,
	type: HapticType = HapticType.LIGHT,
	role: Role? = null,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = null,
	onClick: () -> Unit,
): Modifier {
	return composed {
		val haptic = rememberPlatformHaptic(type)
		val currentOnClick = rememberUpdatedState(onClick)
		val currentOnClickWithHaptic = {
			haptic()
			currentOnClick.value()
		}
		if (interactionSource != null || indication != null) {
			clickable(
				interactionSource = interactionSource,
				indication = indication,
				enabled = enabled,
				role = role,
				onClick = currentOnClickWithHaptic,
			)
		} else {
			clickable(
				enabled = enabled,
				role = role,
				onClick = currentOnClickWithHaptic,
			)
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
		val haptic = rememberPlatformHaptic(type)
		val currentOnClick = rememberUpdatedState(onClick)
		selectable(selected = selected, enabled = enabled, role = role) {
			if (!selected) {
				haptic()
			}
			currentOnClick.value()
		}
	}
}
