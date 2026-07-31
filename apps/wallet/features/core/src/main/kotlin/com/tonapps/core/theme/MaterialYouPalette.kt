package com.tonapps.core.theme

import androidx.compose.ui.graphics.Color
import ui.theme.AppColorScheme
import ui.theme.AppThemeAppearance
import ui.theme.color.AccentColorScheme
import ui.theme.color.BackgroundColorScheme
import ui.theme.color.ButtonColorScheme
import ui.theme.color.FieldColorScheme
import ui.theme.color.IconColorScheme
import ui.theme.color.SeparatorColorScheme
import ui.theme.color.TabBarColorScheme
import ui.theme.color.TextColorScheme

data class MaterialYouPalette(
	val seedColor: Int,
	val dark: Boolean,
	val primary: Int,
	val onPrimary: Int,
	val primaryContainer: Int,
	val onPrimaryContainer: Int,
	val secondary: Int,
	val onSecondary: Int,
	val secondaryContainer: Int,
	val onSecondaryContainer: Int,
	val tertiary: Int,
	val onTertiary: Int,
	val tertiaryContainer: Int,
	val onTertiaryContainer: Int,
	val error: Int,
	val onError: Int,
	val errorContainer: Int,
	val onErrorContainer: Int,
	val background: Int,
	val onBackground: Int,
	val surface: Int,
	val onSurface: Int,
	val onSurfaceVariant: Int,
	val surfaceContainerLowest: Int,
	val surfaceContainerLow: Int,
	val surfaceContainer: Int,
	val surfaceContainerHigh: Int,
	val surfaceContainerHighest: Int,
	val inverseSurface: Int,
	val inverseOnSurface: Int,
	val outline: Int,
	val outlineVariant: Int,
	val scrim: Int,
)

fun MaterialYouPalette.toAppColorScheme(): AppColorScheme {
	val success = Color(if (dark) 0xFF39CC83 else 0xFF25B86F)
	val warning = Color(0xFFF5A73B)
	val purple = Color(tertiary)
	val primaryColor = Color(primary)
	val onSurfaceColor = Color(onSurface)
	val surfaceColor = Color(surface)
	val errorColor = Color(error)

	return AppColorScheme(
		appearance = if (dark) AppThemeAppearance.Dark else AppThemeAppearance.Light,
		text = TextColorScheme(
			primary = onSurfaceColor,
			secondary = Color(onSurfaceVariant),
			tertiary = Color(outline),
			accent = primaryColor,
			primaryAlternate = Color(inverseOnSurface),
		),
		background = BackgroundColorScheme(
			page = surfaceColor,
			pageAlternate = Color(surfaceContainerLowest),
			transparent = surfaceColor.copy(alpha = 0.96f),
			content = Color(surfaceContainerLow),
			contentAlternate = Color(surfaceContainer),
			contentTint = Color(surfaceContainerHigh),
			contentAttention = Color(surfaceContainerHighest),
			highlighted = onSurfaceColor.copy(alpha = 0.08f),
			overlayStrong = Color(scrim).copy(alpha = 0.72f),
			overlayLight = Color(scrim).copy(alpha = 0.48f),
			overlayExtraLight = Color(scrim).copy(alpha = 0.24f),
		),
		icon = IconColorScheme(
			primary = onSurfaceColor,
			secondary = Color(onSurfaceVariant),
			tertiary = Color(outline),
			primaryAlternate = Color(inverseOnSurface),
		),
		buttonPrimary = ButtonColorScheme(
			primaryBackground = primaryColor,
			primaryBackgroundDisable = primaryColor.copy(alpha = 0.38f),
			primaryBackgroundHighlighted = Color(primaryContainer),
			primaryForeground = Color(onPrimary),
		),
		buttonSecondary = ButtonColorScheme(
			primaryBackground = Color(surfaceContainerLow),
			primaryBackgroundDisable = Color(surfaceContainerLow).copy(alpha = 0.38f),
			primaryBackgroundHighlighted = Color(surfaceContainer),
			primaryForeground = onSurfaceColor,
		),
		buttonTertiary = ButtonColorScheme(
			primaryBackground = Color(surfaceContainerHigh),
			primaryBackgroundDisable = Color(surfaceContainerHigh).copy(alpha = 0.38f),
			primaryBackgroundHighlighted = Color(surfaceContainerHighest),
			primaryForeground = onSurfaceColor,
		),
		buttonGreen = ButtonColorScheme(
			primaryBackground = success,
			primaryBackgroundDisable = success.copy(alpha = 0.64f),
			primaryBackgroundHighlighted = success.copy(alpha = 0.84f),
			primaryForeground = Color.White,
		),
		buttonOrange = ButtonColorScheme(
			primaryBackground = warning,
			primaryBackgroundDisable = warning.copy(alpha = 0.64f),
			primaryBackgroundHighlighted = warning.copy(alpha = 0.84f),
			primaryForeground = Color.White,
		),
		field = FieldColorScheme(
			background = Color(surfaceContainerHigh),
			activeBorder = primaryColor,
			errorBorder = errorColor,
			errorBackground = Color(errorContainer),
		),
		accent = AccentColorScheme(
			blue = primaryColor,
			green = success,
			red = errorColor,
			orange = warning,
			purple = purple,
		),
		tabBar = TabBarColorScheme(
			activeIcon = primaryColor,
			inactiveIcon = Color(outline),
		),
		separator = SeparatorColorScheme(
			common = Color(outlineVariant),
			alternate = Color(outlineVariant).copy(alpha = 0.5f),
		),
	)
}
