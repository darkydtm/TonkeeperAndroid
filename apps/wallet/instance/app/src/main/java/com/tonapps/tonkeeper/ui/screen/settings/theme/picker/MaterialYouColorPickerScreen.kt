package com.tonapps.tonkeeper.ui.screen.settings.theme.picker

import android.os.Bundle
import android.view.View
import com.tonapps.core.ComposableFragment
import uikit.base.BaseFragment

class MaterialYouColorPickerScreen : ComposableFragment(), BaseFragment.BottomSheet {
	private val initialColor: Int by lazy {
		requireArguments().getInt(ARG_INITIAL_COLOR)
	}

	override val fragmentName: String = "MaterialYouColorPickerScreen"

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setContent {
			MaterialYouColorPicker(
				initialColor = initialColor,
				onCancel = ::finish,
				onApply = ::applyColor,
			)
		}
	}

	private fun applyColor(color: Int) {
		setResult(
			Bundle().apply {
				putInt(RESULT_COLOR, color)
			},
		)
	}

	companion object {
		const val RESULT_COLOR = "color"
		private const val ARG_INITIAL_COLOR = "initial_color"

		fun newInstance(initialColor: Int): MaterialYouColorPickerScreen {
			return MaterialYouColorPickerScreen().apply {
				arguments = Bundle().apply {
					putInt(ARG_INITIAL_COLOR, initialColor)
				}
			}
		}
	}
}
