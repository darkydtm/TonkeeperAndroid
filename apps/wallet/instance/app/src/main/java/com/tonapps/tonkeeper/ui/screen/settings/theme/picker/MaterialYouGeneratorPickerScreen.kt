package com.tonapps.tonkeeper.ui.screen.settings.theme.picker

import android.os.Bundle
import android.view.View
import com.tonapps.core.ComposableFragment
import com.tonapps.wallet.data.core.MaterialYouGenerator
import uikit.base.BaseFragment

class MaterialYouGeneratorPickerScreen : ComposableFragment(), BaseFragment.BottomSheet {
	private val selectedGenerator: MaterialYouGenerator by lazy {
		MaterialYouGenerator.fromStorageKey(
			requireArguments().getString(ARG_SELECTED_GENERATOR),
		)
	}

	override val fragmentName: String = "MaterialYouGeneratorPickerScreen"

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setContent {
			MaterialYouGeneratorPicker(
				selectedGenerator = selectedGenerator,
				onGeneratorSelected = ::applyGenerator,
			)
		}
	}

	private fun applyGenerator(generator: MaterialYouGenerator) {
		setResult(
			Bundle().apply {
				putString(RESULT_GENERATOR, generator.storageKey)
			},
		)
	}

	companion object {
		const val RESULT_GENERATOR = "generator"
		private const val ARG_SELECTED_GENERATOR = "selected_generator"

		fun newInstance(generator: MaterialYouGenerator): MaterialYouGeneratorPickerScreen {
			return MaterialYouGeneratorPickerScreen().apply {
				arguments = Bundle().apply {
					putString(ARG_SELECTED_GENERATOR, generator.storageKey)
				}
			}
		}
	}
}
