package com.tonapps.tonkeeper.extensions

import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import com.tonapps.core.theme.MaterialYouTheme
import com.tonapps.wallet.data.core.MaterialYouSettings
import com.tonapps.wallet.data.core.Theme
import ui.theme.AppColorScheme

@Composable
fun Theme.compose(
	context: Context,
	materialYouSettings: MaterialYouSettings,
): AppColorScheme {
	if (isMaterialYou && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
		return MaterialYouTheme.appColorScheme(
			context = context,
			settings = materialYouSettings,
			dark = context.isDarkMode,
		)
	}
	return when (key) {
		Theme.DARK_KEY -> ui.theme.appColorSchemeDark()
		Theme.LIGHT_KEY -> ui.theme.appColorSchemeLight()
		else -> ui.theme.appColorSchemeBlue()
	}
}
