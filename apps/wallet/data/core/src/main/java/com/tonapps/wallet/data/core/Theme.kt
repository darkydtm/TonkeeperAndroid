package com.tonapps.wallet.data.core

data class Theme(
	val key: String,
	val resId: Int,
	val light: Boolean,
	val title: String,
) {

	val isSystem: Boolean
		get() = key == SYSTEM_KEY || key == AUTO_KEY

	val isMaterialYou: Boolean
		get() = key == MATERIAL_YOU_KEY

	val usesSystemAppearance: Boolean
		get() = isSystem || isMaterialYou

	companion object {
		const val BLUE_KEY = "blue"
		const val DARK_KEY = "dark"
		const val LIGHT_KEY = "light"
		const val SYSTEM_KEY = "system"
		const val MATERIAL_YOU_KEY = "material_you"
		private const val AUTO_KEY = "auto"

		private val supportedThemes = mutableListOf<Theme>()

		fun clear() {
			supportedThemes.clear()
		}

		fun getByKey(key: String): Theme {
			return supportedThemes.firstOrNull { it.key == key }
				?: supportedThemes.firstOrNull { key == MATERIAL_YOU_KEY && it.key == SYSTEM_KEY }
				?: supportedThemes.first()
		}

		fun getByResId(resId: Int): Theme {
			return supportedThemes.firstOrNull { it.resId == resId } ?: supportedThemes.first()
		}

		fun add(key: String, resId: Int, light: Boolean = false, title: String) {
			supportedThemes.add(Theme(key, resId, light, title))
		}

		fun getSupported(): List<Theme> {
			return supportedThemes
		}
	}
}
