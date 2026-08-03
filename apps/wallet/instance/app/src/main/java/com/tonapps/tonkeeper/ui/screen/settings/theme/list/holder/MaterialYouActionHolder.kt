package com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder

import uikit.extensions.setHapticClickListener

import android.view.ViewGroup
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.Item
import uikit.widget.item.ItemIconView

class MaterialYouActionHolder(
	parent: ViewGroup,
	private val onClick: (action: Item.MaterialYouAction.Action) -> Unit,
): Holder<Item.MaterialYouAction>(ItemIconView(parent.context)) {
	private val actionView = itemView as ItemIconView

	override fun onBind(item: Item.MaterialYouAction) {
		actionView.position = item.position
		actionView.text = item.title
		actionView.description = item.description
		actionView.setHapticClickListener { onClick(item.action) }
	}
}
