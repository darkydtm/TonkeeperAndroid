package com.tonapps.core.theme

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.color.utilities.DynamicColor
import com.google.android.material.color.utilities.DynamicScheme
import com.google.android.material.color.utilities.Hct
import com.google.android.material.color.utilities.MaterialDynamicColors
import com.google.android.material.color.utilities.SchemeContent
import com.google.android.material.color.utilities.SchemeExpressive
import com.google.android.material.color.utilities.SchemeFidelity
import com.google.android.material.color.utilities.SchemeFruitSalad
import com.google.android.material.color.utilities.SchemeMonochrome
import com.google.android.material.color.utilities.SchemeNeutral
import com.google.android.material.color.utilities.SchemeRainbow
import com.google.android.material.color.utilities.SchemeTonalSpot
import com.google.android.material.color.utilities.SchemeVibrant
import com.tonapps.wallet.data.core.MaterialYouColorSource
import com.tonapps.wallet.data.core.MaterialYouGenerator
import com.tonapps.wallet.data.core.MaterialYouSettings
import ui.theme.AppColorScheme

@SuppressLint("RestrictedApi")
object MaterialYouTheme {
	private val colors = MaterialDynamicColors()

	@RequiresApi(Build.VERSION_CODES.S)
	fun appColorScheme(
		context: Context,
		settings: MaterialYouSettings,
		dark: Boolean,
	): AppColorScheme {
		return palette(context, settings, dark).toAppColorScheme()
	}

	@RequiresApi(Build.VERSION_CODES.S)
	fun palette(
		context: Context,
		settings: MaterialYouSettings,
		dark: Boolean,
	): MaterialYouPalette {
		val seedColor = resolveSeedColor(context, settings)
		if (settings.generator == MaterialYouGenerator.SYSTEM && settings.useWallpaperColors) {
			val systemColors = if (dark) {
				dynamicDarkColorScheme(context)
			} else {
				dynamicLightColorScheme(context)
			}
			return systemColors.toMaterialYouPalette(seedColor, dark, settings.amoled)
		}
		val scheme = createScheme(seedColor, settings.generator, dark)
		return MaterialYouPalette(
			seedColor = seedColor,
			dark = dark,
			primary = colors.primary().argb(scheme),
			onPrimary = colors.onPrimary().argb(scheme),
			primaryContainer = colors.primaryContainer().argb(scheme),
			onPrimaryContainer = colors.onPrimaryContainer().argb(scheme),
			secondary = colors.secondary().argb(scheme),
			onSecondary = colors.onSecondary().argb(scheme),
			secondaryContainer = colors.secondaryContainer().argb(scheme),
			onSecondaryContainer = colors.onSecondaryContainer().argb(scheme),
			tertiary = colors.tertiary().argb(scheme),
			onTertiary = colors.onTertiary().argb(scheme),
			tertiaryContainer = colors.tertiaryContainer().argb(scheme),
			onTertiaryContainer = colors.onTertiaryContainer().argb(scheme),
			error = colors.error().argb(scheme),
			onError = colors.onError().argb(scheme),
			errorContainer = colors.errorContainer().argb(scheme),
			onErrorContainer = colors.onErrorContainer().argb(scheme),
			background = colors.background().argb(scheme),
			onBackground = colors.onBackground().argb(scheme),
			surface = colors.surface().argb(scheme),
			onSurface = colors.onSurface().argb(scheme),
			onSurfaceVariant = colors.onSurfaceVariant().argb(scheme),
			surfaceContainerLowest = colors.surfaceContainerLowest().argb(scheme),
			surfaceContainerLow = colors.surfaceContainerLow().argb(scheme),
			surfaceContainer = colors.surfaceContainer().argb(scheme),
			surfaceContainerHigh = colors.surfaceContainerHigh().argb(scheme),
			surfaceContainerHighest = colors.surfaceContainerHighest().argb(scheme),
			inverseSurface = colors.inverseSurface().argb(scheme),
			inverseOnSurface = colors.inverseOnSurface().argb(scheme),
			outline = colors.outline().argb(scheme),
			outlineVariant = colors.outlineVariant().argb(scheme),
			scrim = colors.scrim().argb(scheme),
		).withAmoled(settings.amoled)
	}

	@RequiresApi(Build.VERSION_CODES.S)
	fun resolveSeedColor(context: Context, settings: MaterialYouSettings): Int {
		if (settings.useWallpaperColors) {
			return context.getColor(android.R.color.system_accent1_500)
		}
		return when (settings.colorSource) {
			MaterialYouColorSource.PRESET -> settings.preset.color
			MaterialYouColorSource.CUSTOM -> settings.customColor
		}
	}

	private fun createScheme(
		seedColor: Int,
		generator: MaterialYouGenerator,
		dark: Boolean,
	): DynamicScheme {
		val source = Hct.fromInt(seedColor)
		return when (generator) {
			MaterialYouGenerator.SYSTEM,
			MaterialYouGenerator.TONAL_SPOT -> SchemeTonalSpot(source, dark, 0.0)
			MaterialYouGenerator.VIBRANT -> SchemeVibrant(source, dark, 0.0)
			MaterialYouGenerator.EXPRESSIVE -> SchemeExpressive(source, dark, 0.0)
			MaterialYouGenerator.NEUTRAL -> SchemeNeutral(source, dark, 0.0)
			MaterialYouGenerator.MONOCHROME -> SchemeMonochrome(source, dark, 0.0)
			MaterialYouGenerator.RAINBOW -> SchemeRainbow(source, dark, 0.0)
			MaterialYouGenerator.FRUIT_SALAD -> SchemeFruitSalad(source, dark, 0.0)
			MaterialYouGenerator.FIDELITY -> SchemeFidelity(source, dark, 0.0)
			MaterialYouGenerator.CONTENT -> SchemeContent(source, dark, 0.0)
		}
	}

	private fun DynamicColor.argb(scheme: DynamicScheme): Int {
		return getArgb(scheme)
	}

	private fun ColorScheme.toMaterialYouPalette(
		seedColor: Int,
		dark: Boolean,
		amoled: Boolean,
	): MaterialYouPalette {
		return MaterialYouPalette(
			seedColor = seedColor,
			dark = dark,
			primary = primary.toArgb(),
			onPrimary = onPrimary.toArgb(),
			primaryContainer = primaryContainer.toArgb(),
			onPrimaryContainer = onPrimaryContainer.toArgb(),
			secondary = secondary.toArgb(),
			onSecondary = onSecondary.toArgb(),
			secondaryContainer = secondaryContainer.toArgb(),
			onSecondaryContainer = onSecondaryContainer.toArgb(),
			tertiary = tertiary.toArgb(),
			onTertiary = onTertiary.toArgb(),
			tertiaryContainer = tertiaryContainer.toArgb(),
			onTertiaryContainer = onTertiaryContainer.toArgb(),
			error = error.toArgb(),
			onError = onError.toArgb(),
			errorContainer = errorContainer.toArgb(),
			onErrorContainer = onErrorContainer.toArgb(),
			background = background.toArgb(),
			onBackground = onBackground.toArgb(),
			surface = surface.toArgb(),
			onSurface = onSurface.toArgb(),
			onSurfaceVariant = onSurfaceVariant.toArgb(),
			surfaceContainerLowest = surfaceContainerLowest.toArgb(),
			surfaceContainerLow = surfaceContainerLow.toArgb(),
			surfaceContainer = surfaceContainer.toArgb(),
			surfaceContainerHigh = surfaceContainerHigh.toArgb(),
			surfaceContainerHighest = surfaceContainerHighest.toArgb(),
			inverseSurface = inverseSurface.toArgb(),
			inverseOnSurface = inverseOnSurface.toArgb(),
			outline = outline.toArgb(),
			outlineVariant = outlineVariant.toArgb(),
			scrim = scrim.toArgb(),
		).withAmoled(amoled)
	}

	private fun MaterialYouPalette.withAmoled(enabled: Boolean): MaterialYouPalette {
		if (!enabled || !dark) {
			return this
		}
		return copy(
			background = 0xFF000000.toInt(),
			surface = 0xFF000000.toInt(),
			surfaceContainerLowest = 0xFF000000.toInt(),
		)
	}
}
