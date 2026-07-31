package com.tonapps.tonkeeper

import android.app.Application
import android.content.res.Configuration
import android.os.Build
import com.tonapps.icu.CurrencyFormatter
import com.tonapps.wallet.data.core.Theme
import com.tonapps.wallet.localization.Localization
import uikit.R

open class App : Application() {

    companion object {
        @Deprecated("Initialize object explicitly")
        lateinit var instance: App

        @Deprecated("Migration to Compose")
        fun applyConfiguration(newConfig: Configuration) {
            CurrencyFormatter.onConfigurationChanged(newConfig)
        }
    }

	fun updateThemes() {
		Theme.clear()
		Theme.add(Theme.BLUE_KEY, R.style.Theme_App_Blue, title = getString(Localization.theme_deep_blue))
		Theme.add(Theme.DARK_KEY, R.style.Theme_App_Dark, title = getString(Localization.theme_dark))
		Theme.add(
			Theme.LIGHT_KEY,
			R.style.Theme_App_Light,
			true,
			title = getString(Localization.theme_light)
		)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			Theme.add(Theme.MATERIAL_YOU_KEY, 0, title = getString(Localization.material_you))
		}
		Theme.add(Theme.SYSTEM_KEY, 0, title = getString(Localization.system))
	}
}
