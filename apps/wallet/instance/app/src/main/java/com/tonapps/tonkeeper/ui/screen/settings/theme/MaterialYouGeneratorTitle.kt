package com.tonapps.tonkeeper.ui.screen.settings.theme

import androidx.annotation.StringRes
import com.tonapps.wallet.data.core.MaterialYouGenerator
import com.tonapps.wallet.localization.Localization

@StringRes
fun MaterialYouGenerator.titleRes(): Int {
	return when (this) {
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
	}
}
