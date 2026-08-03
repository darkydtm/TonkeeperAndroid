package ui.haptic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import uikit.HapticHelper

@Composable
internal actual fun rememberPlatformHaptic(type: HapticType): () -> Unit {
	val context = LocalContext.current
	return remember(context, type) {
		{
			HapticHelper.perform(context, type.toUIKitType())
		}
	}
}

private fun HapticType.toUIKitType(): uikit.HapticType = when (this) {
	HapticType.LIGHT -> uikit.HapticType.LIGHT
	HapticType.SELECTION -> uikit.HapticType.SELECTION
	HapticType.CONFIRM -> uikit.HapticType.CONFIRM
	HapticType.SUCCESS -> uikit.HapticType.SUCCESS
	HapticType.WARNING -> uikit.HapticType.WARNING
	HapticType.ERROR -> uikit.HapticType.ERROR
}
