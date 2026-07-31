package com.tonapps.tonkeeper.manager.theme

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.color.ColorResourcesOverride
import com.tonapps.core.theme.MaterialYouPalette
import com.tonapps.core.theme.toAppColorScheme
import com.tonapps.ui.uikit.color.R as ColorR

@SuppressLint("RestrictedApi")
object MaterialYouResources {
	@RequiresApi(Build.VERSION_CODES.S)
	fun apply(context: Context, palette: MaterialYouPalette): Boolean {
		val scheme = palette.toAppColorScheme()
		val colors = if (palette.dark) {
			mapOf(
				ColorR.color.textPrimaryDark to scheme.text.primary.toArgb(),
				ColorR.color.textSecondaryDark to scheme.text.secondary.toArgb(),
				ColorR.color.textTertiaryDark to scheme.text.tertiary.toArgb(),
				ColorR.color.textAccentDark to scheme.text.accent.toArgb(),
				ColorR.color.textPrimaryAlternateDark to scheme.text.primaryAlternate.toArgb(),
				ColorR.color.backgroundPageDark to scheme.background.page.toArgb(),
				ColorR.color.backgroundTransparentDark to scheme.background.transparent.toArgb(),
				ColorR.color.backgroundContentDark to scheme.background.content.toArgb(),
				ColorR.color.backgroundContentTintDark to scheme.background.contentTint.toArgb(),
				ColorR.color.backgroundContentAttentionDark to scheme.background.contentAttention.toArgb(),
				ColorR.color.backgroundHighlightedDark to scheme.background.highlighted.toArgb(),
				ColorR.color.backgroundOverlayStrongDark to scheme.background.overlayStrong.toArgb(),
				ColorR.color.backgroundOverlayLightDark to scheme.background.overlayLight.toArgb(),
				ColorR.color.backgroundOverlayExtraLightDark to scheme.background.overlayExtraLight.toArgb(),
				ColorR.color.iconPrimaryDark to scheme.icon.primary.toArgb(),
				ColorR.color.iconSecondaryDark to scheme.icon.secondary.toArgb(),
				ColorR.color.iconTertiaryDark to scheme.icon.tertiary.toArgb(),
				ColorR.color.iconPrimaryAlternateDark to scheme.icon.primaryAlternate.toArgb(),
				ColorR.color.buttonPrimaryBackgroundDark to scheme.buttonPrimary.primaryBackground.toArgb(),
				ColorR.color.buttonPrimaryBackgroundDisabledDark to scheme.buttonPrimary.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonPrimaryBackgroundHighlightedDark to scheme.buttonPrimary.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.buttonPrimaryForegroundDark to scheme.buttonPrimary.primaryForeground.toArgb(),
				ColorR.color.buttonSecondaryBackgroundDark to scheme.buttonSecondary.primaryBackground.toArgb(),
				ColorR.color.buttonSecondaryBackgroundDisabledDark to scheme.buttonSecondary.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonSecondaryBackgroundHighlightedDark to scheme.buttonSecondary.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.buttonSecondaryForegroundDark to scheme.buttonSecondary.primaryForeground.toArgb(),
				ColorR.color.buttonTertiaryBackgroundDark to scheme.buttonTertiary.primaryBackground.toArgb(),
				ColorR.color.buttonTertiaryBackgroundDisabledDark to scheme.buttonTertiary.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonTertiaryBackgroundHighlightedDark to scheme.buttonTertiary.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.buttonTertiaryForegroundDark to scheme.buttonTertiary.primaryForeground.toArgb(),
				ColorR.color.buttonGreenBackgroundDark to scheme.buttonGreen.primaryBackground.toArgb(),
				ColorR.color.buttonGreenBackgroundDisabledDark to scheme.buttonGreen.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonGreenBackgroundHighlightedDark to scheme.buttonGreen.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.buttonOrangeBackgroundDark to scheme.buttonOrange.primaryBackground.toArgb(),
				ColorR.color.buttonOrangeBackgroundDisabledDark to scheme.buttonOrange.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonOrangeBackgroundHighlightedDark to scheme.buttonOrange.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.fieldBackgroundDark to scheme.field.background.toArgb(),
				ColorR.color.fieldActiveBorderDark to scheme.field.activeBorder.toArgb(),
				ColorR.color.fieldErrorBorderDark to scheme.field.errorBorder.toArgb(),
				ColorR.color.fieldErrorBackgroundDark to scheme.field.errorBackground.toArgb(),
				ColorR.color.accentBlueDark to scheme.accent.blue.toArgb(),
				ColorR.color.accentGreenDark to scheme.accent.green.toArgb(),
				ColorR.color.accentRedDark to scheme.accent.red.toArgb(),
				ColorR.color.accentOrangeDark to scheme.accent.orange.toArgb(),
				ColorR.color.accentPurpleDark to scheme.accent.purple.toArgb(),
				ColorR.color.tabBarActiveIconDark to scheme.tabBar.activeIcon.toArgb(),
				ColorR.color.tabBarInactiveIconDark to scheme.tabBar.inactiveIcon.toArgb(),
				ColorR.color.separatorCommonDark to scheme.separator.common.toArgb(),
				ColorR.color.separatorAlternateDark to scheme.separator.alternate.toArgb(),
			)
		} else {
			mapOf(
				ColorR.color.textPrimaryLight to scheme.text.primary.toArgb(),
				ColorR.color.textSecondaryLight to scheme.text.secondary.toArgb(),
				ColorR.color.textTertiaryLight to scheme.text.tertiary.toArgb(),
				ColorR.color.textAccentLight to scheme.text.accent.toArgb(),
				ColorR.color.textPrimaryAlternateLight to scheme.text.primaryAlternate.toArgb(),
				ColorR.color.backgroundPageLight to scheme.background.page.toArgb(),
				ColorR.color.backgroundTransparentLight to scheme.background.transparent.toArgb(),
				ColorR.color.backgroundContentLight to scheme.background.content.toArgb(),
				ColorR.color.backgroundContentTintLight to scheme.background.contentTint.toArgb(),
				ColorR.color.backgroundContentAttentionLight to scheme.background.contentAttention.toArgb(),
				ColorR.color.backgroundHighlightedLight to scheme.background.highlighted.toArgb(),
				ColorR.color.backgroundOverlayStrongLight to scheme.background.overlayStrong.toArgb(),
				ColorR.color.backgroundOverlayLightLight to scheme.background.overlayLight.toArgb(),
				ColorR.color.backgroundOverlayExtraLightLight to scheme.background.overlayExtraLight.toArgb(),
				ColorR.color.iconPrimaryLight to scheme.icon.primary.toArgb(),
				ColorR.color.iconSecondaryLight to scheme.icon.secondary.toArgb(),
				ColorR.color.iconTertiaryLight to scheme.icon.tertiary.toArgb(),
				ColorR.color.iconPrimaryAlternateLight to scheme.icon.primaryAlternate.toArgb(),
				ColorR.color.buttonPrimaryBackgroundLight to scheme.buttonPrimary.primaryBackground.toArgb(),
				ColorR.color.buttonPrimaryBackgroundDisabledLight to scheme.buttonPrimary.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonPrimaryBackgroundHighlightedLight to scheme.buttonPrimary.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.buttonPrimaryForegroundLight to scheme.buttonPrimary.primaryForeground.toArgb(),
				ColorR.color.buttonSecondaryBackgroundLight to scheme.buttonSecondary.primaryBackground.toArgb(),
				ColorR.color.buttonSecondaryBackgroundDisabledLight to scheme.buttonSecondary.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonSecondaryBackgroundHighlightedLight to scheme.buttonSecondary.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.buttonSecondaryForegroundLight to scheme.buttonSecondary.primaryForeground.toArgb(),
				ColorR.color.buttonTertiaryBackgroundLight to scheme.buttonTertiary.primaryBackground.toArgb(),
				ColorR.color.buttonTertiaryBackgroundDisabledLight to scheme.buttonTertiary.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonTertiaryBackgroundHighlightedLight to scheme.buttonTertiary.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.buttonTertiaryForegroundLight to scheme.buttonTertiary.primaryForeground.toArgb(),
				ColorR.color.buttonGreenBackgroundLight to scheme.buttonGreen.primaryBackground.toArgb(),
				ColorR.color.buttonGreenBackgroundDisabledLight to scheme.buttonGreen.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonGreenBackgroundHighlightedLight to scheme.buttonGreen.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.buttonOrangeBackgroundLight to scheme.buttonOrange.primaryBackground.toArgb(),
				ColorR.color.buttonOrangeBackgroundDisabledLight to scheme.buttonOrange.primaryBackgroundDisable.toArgb(),
				ColorR.color.buttonOrangeBackgroundHighlightedLight to scheme.buttonOrange.primaryBackgroundHighlighted.toArgb(),
				ColorR.color.fieldBackgroundLight to scheme.field.background.toArgb(),
				ColorR.color.fieldActiveBorderLight to scheme.field.activeBorder.toArgb(),
				ColorR.color.fieldErrorBorderLight to scheme.field.errorBorder.toArgb(),
				ColorR.color.fieldErrorBackgroundLight to scheme.field.errorBackground.toArgb(),
				ColorR.color.accentBlueLight to scheme.accent.blue.toArgb(),
				ColorR.color.accentGreenLight to scheme.accent.green.toArgb(),
				ColorR.color.accentRedLight to scheme.accent.red.toArgb(),
				ColorR.color.accentOrangeLight to scheme.accent.orange.toArgb(),
				ColorR.color.accentPurpleLight to scheme.accent.purple.toArgb(),
				ColorR.color.tabBarActiveIconLight to scheme.tabBar.activeIcon.toArgb(),
				ColorR.color.tabBarInactiveIconLight to scheme.tabBar.inactiveIcon.toArgb(),
				ColorR.color.separatorCommonLight to scheme.separator.common.toArgb(),
				ColorR.color.separatorAlternateLight to scheme.separator.alternate.toArgb(),
			)
		}
		return ColorResourcesOverride.getInstance()?.applyIfPossible(context, colors) == true
	}
}
