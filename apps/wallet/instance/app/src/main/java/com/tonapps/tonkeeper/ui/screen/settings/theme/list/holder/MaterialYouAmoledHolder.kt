package com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder

import android.view.ViewGroup
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.Item
import com.tonapps.wallet.localization.Localization
import uikit.widget.item.ItemSwitchView

class MaterialYouAmoledHolder(
	parent: ViewGroup,
	private val onCheckedChanged: (enabled: Boolean) -> Unit,
): Holder<Item.MaterialYouAmoled>(ItemSwitchView(parent.context)) {
	private val switchView = itemView as ItemSwitchView

	init {
		switchView.text = getString(Localization.material_you_amoled)
		switchView.subtitle = getString(Localization.material_you_amoled_description)
		switchView.doOnCheckedChanged = { checked, byUser ->
			if (byUser) {
				onCheckedChanged(checked)
			}
		}
	}

	override fun onBind(item: Item.MaterialYouAmoled) {
		switchView.position = item.position
		switchView.setChecked(item.enabled, false)
	}
}
