package com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder

import android.view.ViewGroup
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.Item
import com.tonapps.wallet.localization.Localization
import uikit.widget.item.ItemSwitchView

class WallpaperColorsHolder(
	parent: ViewGroup,
	private val onCheckedChanged: (enabled: Boolean) -> Unit,
): Holder<Item.WallpaperColors>(ItemSwitchView(parent.context)) {
	private val switchView = itemView as ItemSwitchView

	init {
		switchView.text = getString(Localization.use_wallpaper_colors)
		switchView.subtitle = getString(Localization.use_wallpaper_colors_description)
		switchView.doOnCheckedChanged = { checked, byUser ->
			if (byUser) {
				onCheckedChanged(checked)
			}
		}
	}

	override fun onBind(item: Item.WallpaperColors) {
		switchView.position = item.position
		switchView.setChecked(item.enabled, false)
	}
}
