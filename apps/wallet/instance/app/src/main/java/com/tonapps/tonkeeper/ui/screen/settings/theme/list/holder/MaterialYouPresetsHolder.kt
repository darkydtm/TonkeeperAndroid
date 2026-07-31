package com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder

import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.Item
import com.tonapps.wallet.data.core.MaterialYouPreset
import uikit.extensions.dp

class MaterialYouPresetsHolder(
	parent: ViewGroup,
	private val onPresetSelected: (preset: MaterialYouPreset) -> Unit,
): Holder<Item.MaterialYouPresets>(HorizontalScrollView(parent.context)) {
	private val container = LinearLayout(parent.context).apply {
		orientation = LinearLayout.HORIZONTAL
		setPadding(8.dp, 8.dp, 8.dp, 8.dp)
	}

	init {
		(itemView as HorizontalScrollView).apply {
			isHorizontalScrollBarEnabled = false
			addView(container)
			layoutParams = RecyclerView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				64.dp,
			)
		}
	}

	override fun onBind(item: Item.MaterialYouPresets) {
		container.removeAllViews()
		MaterialYouPreset.entries.forEach { preset ->
			container.addView(
				MaterialYouPresetView(container.context).apply {
					bind(
						preset = preset,
						title = item.titles.getValue(preset),
						selected = item.selected == preset,
						onClick = onPresetSelected,
					)
				},
			)
		}
	}
}
