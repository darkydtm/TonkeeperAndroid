package com.tonapps.tonkeeper.ui.screen.settings.theme.list

import com.tonapps.tonkeeper.core.LauncherIcon
import com.tonapps.uikit.list.BaseListItem
import com.tonapps.uikit.list.ListCell
import com.tonapps.wallet.data.core.MaterialYouPreset

sealed class Item(type: Int): BaseListItem(type) {

    companion object {
        const val TYPE_THEME = 0
        const val TYPE_TITLE = 1
        const val TYPE_ICON = 2
        const val TYPE_SPACE = 3
		const val TYPE_FONT_SIZE = 4
		const val TYPE_WALLPAPER_COLORS = 5
		const val TYPE_MATERIAL_YOU_ACTION = 6
		const val TYPE_MATERIAL_YOU_PRESETS = 7
    }

    data class Theme(
        val position: ListCell.Position,
        val theme: com.tonapps.wallet.data.core.Theme,
        val selected: Boolean
    ): Item(TYPE_THEME) {

        val title: String
            get() = theme.title
    }

    data class Title(
        val title: String
    ): Item(TYPE_TITLE)

    data class Icon(
        val icon: LauncherIcon
    ): Item(TYPE_ICON)

    data object Space: Item(TYPE_SPACE)

	data object FontSize: Item(TYPE_FONT_SIZE)

	data class WallpaperColors(
		val position: ListCell.Position,
		val enabled: Boolean,
	): Item(TYPE_WALLPAPER_COLORS)

	data class MaterialYouAction(
		val position: ListCell.Position,
		val title: String,
		val description: String,
		val action: Action,
	): Item(TYPE_MATERIAL_YOU_ACTION) {
		enum class Action {
			GENERATOR,
			CUSTOM_COLOR,
		}
	}

	data class MaterialYouPresets(
		val selected: MaterialYouPreset?,
		val titles: Map<MaterialYouPreset, String>,
	): Item(TYPE_MATERIAL_YOU_PRESETS)
}
