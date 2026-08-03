package ui.haptic

import androidx.compose.runtime.Composable

@Composable
internal expect fun rememberPlatformHaptic(type: HapticType): () -> Unit
