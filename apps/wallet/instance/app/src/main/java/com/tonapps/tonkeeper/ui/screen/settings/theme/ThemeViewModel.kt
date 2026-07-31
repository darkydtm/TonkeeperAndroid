package com.tonapps.tonkeeper.ui.screen.settings.theme

import android.app.Application
import com.tonapps.extensions.recreate
import com.tonapps.tonkeeper.core.LauncherIcon
import com.tonapps.tonkeeper.ui.base.BaseWalletVM
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.Item
import com.tonapps.uikit.list.ListCell
import com.tonapps.wallet.data.core.Theme
import com.tonapps.wallet.data.core.MaterialYouColorSource
import com.tonapps.wallet.data.core.MaterialYouGenerator
import com.tonapps.wallet.data.core.MaterialYouHexColor
import com.tonapps.wallet.data.core.MaterialYouPreset
import com.tonapps.wallet.data.core.MaterialYouSettings
import com.tonapps.wallet.data.settings.SettingsRepository
import com.tonapps.wallet.localization.Localization
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter

class ThemeViewModel(
    app: Application,
    private val settingsRepository: SettingsRepository
): BaseWalletVM(app) {

	private var currentThemeKey = settingsRepository.theme.key
	val materialYouSettings
		get() = settingsRepository.materialYouSettings

    private val _uiItemsFlow = MutableStateFlow<List<Item>>(emptyList())
    val uiItemsFlow = _uiItemsFlow.asStateFlow().filter { it.isNotEmpty() }

	init {
		updateValues(currentThemeKey)
	}

	fun setTheme(themeKey: String) {
		currentThemeKey = themeKey
		updateValues(themeKey)
		settingsRepository.theme = Theme.getByKey(themeKey)
		context.recreate()
	}

	fun setWallpaperColors(enabled: Boolean) {
		updateMaterialYouSettings {
			copy(useWallpaperColors = enabled)
		}
	}

	fun setGenerator(generator: MaterialYouGenerator) {
		updateMaterialYouSettings {
			copy(generator = generator)
		}
	}

	fun setAmoled(enabled: Boolean) {
		updateMaterialYouSettings {
			copy(amoled = enabled)
		}
	}

	fun setPreset(preset: MaterialYouPreset) {
		updateMaterialYouSettings {
			copy(
				colorSource = MaterialYouColorSource.PRESET,
				preset = preset,
			)
		}
	}

	fun setCustomColor(color: Int) {
		updateMaterialYouSettings {
			copy(
				colorSource = MaterialYouColorSource.CUSTOM,
				customColor = color,
			)
		}
	}

	fun generatorTitle(generator: MaterialYouGenerator): String {
		return getString(
			when (generator) {
				MaterialYouGenerator.SYSTEM -> Localization.material_you_system
				MaterialYouGenerator.TONAL_SPOT -> Localization.material_you_tonal_spot
				MaterialYouGenerator.VIBRANT -> Localization.material_you_vibrant
				MaterialYouGenerator.EXPRESSIVE -> Localization.material_you_expressive
				MaterialYouGenerator.NEUTRAL -> Localization.material_you_neutral
				MaterialYouGenerator.MONOCHROME -> Localization.material_you_monochrome
				MaterialYouGenerator.RAINBOW -> Localization.material_you_rainbow
				MaterialYouGenerator.FRUIT_SALAD -> Localization.material_you_fruit_salad
				MaterialYouGenerator.FIDELITY -> Localization.material_you_fidelity
				MaterialYouGenerator.CONTENT -> Localization.material_you_content
			},
		)
	}

	private fun presetTitle(preset: MaterialYouPreset): String {
		return getString(
			when (preset) {
				MaterialYouPreset.RED -> Localization.color_red
				MaterialYouPreset.ORANGE -> Localization.color_orange
				MaterialYouPreset.YELLOW -> Localization.color_yellow
				MaterialYouPreset.GREEN -> Localization.color_green
				MaterialYouPreset.CYAN -> Localization.color_cyan
				MaterialYouPreset.BLUE -> Localization.color_blue
				MaterialYouPreset.PURPLE -> Localization.color_purple
				MaterialYouPreset.PINK -> Localization.color_pink
			},
		)
	}

	private fun updateMaterialYouSettings(
		block: MaterialYouSettings.() -> MaterialYouSettings,
	) {
		settingsRepository.updateMaterialYouSettings(settingsRepository.materialYouSettings.block())
		updateValues(currentThemeKey)
		context.recreate()
	}

	private fun updateValues(themeKey: String) {
		val items = mutableListOf<Item>()
		items.add(Item.Title(getString(Localization.color_scheme)))
        for ((index, theme) in Theme.getSupported().withIndex()) {
            val position = ListCell.getPosition(Theme.getSupported().size, index)
			items.add(Item.Theme(
				position = position,
				theme = theme,
				selected = themeKey == theme.key,
			))
		}
		if (themeKey == Theme.MATERIAL_YOU_KEY) {
			val materialYouSettings = settingsRepository.materialYouSettings
			items.add(Item.Space)
			items.add(Item.Title(getString(Localization.material_you)))
			items.add(
				Item.WallpaperColors(
					position = ListCell.Position.FIRST,
					enabled = materialYouSettings.useWallpaperColors,
				),
			)
			items.add(
				Item.MaterialYouAction(
					position = ListCell.Position.MIDDLE,
					title = getString(Localization.color_generator),
					description = generatorTitle(materialYouSettings.generator),
					action = Item.MaterialYouAction.Action.GENERATOR,
				),
			)
			items.add(
				Item.MaterialYouAmoled(
					position = ListCell.Position.LAST,
					enabled = materialYouSettings.amoled,
				),
			)
			if (!materialYouSettings.useWallpaperColors) {
				items.add(Item.Title(getString(Localization.base_color)))
				items.add(
					Item.MaterialYouPresets(
						selected = if (materialYouSettings.colorSource == MaterialYouColorSource.PRESET) {
							materialYouSettings.preset
						} else {
							null
						},
						titles = MaterialYouPreset.entries.associateWith(::presetTitle),
					),
				)
				items.add(
					Item.MaterialYouAction(
						position = ListCell.Position.SINGLE,
						title = getString(Localization.custom_color),
						description = MaterialYouHexColor.format(
							materialYouSettings.customColor,
						),
						action = Item.MaterialYouAction.Action.CUSTOM_COLOR,
					),
				)
			}
		}
        items.add(Item.Space)
        items.add(Item.Title(getString(Localization.app_icon)))
        items.add(Item.Icon(LauncherIcon.Default))
        items.add(Item.Icon(LauncherIcon.Accent))
        items.add(Item.Icon(LauncherIcon.Dark))
        items.add(Item.Icon(LauncherIcon.Light))
        items.add(Item.Space)
        items.add(Item.Title(getString(Localization.other)))
        items.add(Item.FontSize)
        _uiItemsFlow.value = items.toList()
    }
}
