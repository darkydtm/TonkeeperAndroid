package com.tonapps.tonkeeper.ui.screen.settings.main.list.holder

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.tonapps.tonkeeper.ui.screen.settings.main.list.Item
import com.tonapps.tonkeeper.ui.screen.settings.main.list.formatHapticStrengthPercent
import com.tonapps.tonkeeperx.R
import com.tonapps.wallet.localization.Localization
import uikit.extensions.drawable

class HapticStrengthHolder(
	parent: ViewGroup,
	onClick: (Item) -> Unit,
	private val onValueCommitted: (Float) -> Unit,
) : Holder<Item.HapticStrength>(parent, R.layout.view_settings_haptic_strength, onClick) {

	private val titleView = findViewById<AppCompatTextView>(R.id.title)
	private val valueView = findViewById<AppCompatTextView>(R.id.value)
	private val sliderView = findViewById<HapticStrengthSliderView>(R.id.slider)

	init {
		titleView.setText(Localization.haptic_strength)
		sliderView.contentDescription = titleView.text
		sliderView.onValueChanged = { value ->
			valueView.text = formatHapticStrengthPercent(value)
		}
		sliderView.onValueCommitted = onValueCommitted
	}

	override fun onBind(item: Item.HapticStrength) {
		itemView.background = item.position.drawable(context)
		sliderView.value = item.value
		valueView.text = formatHapticStrengthPercent(item.value)
	}
}
