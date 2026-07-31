package com.tonapps.tonkeeper.ui.screen.settings.theme

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tonapps.tonkeeper.ui.base.BaseListWalletScreen
import com.tonapps.tonkeeper.ui.base.ScreenContext
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.Adapter
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.Item
import com.tonapps.tonkeeper.ui.screen.settings.theme.picker.MaterialYouColorPickerScreen
import com.tonapps.blockchain.model.legacy.WalletEntity
import com.tonapps.wallet.localization.Localization
import com.tonapps.wallet.data.core.MaterialYouGenerator
import org.koin.androidx.viewmodel.ext.android.viewModel
import uikit.base.BaseFragment
import uikit.extensions.collectFlow
import uikit.extensions.dp
import uikit.navigation.Navigation.Companion.navigation

class ThemeScreen(wallet: WalletEntity): BaseListWalletScreen<ScreenContext.Wallet>(ScreenContext.Wallet(wallet)), BaseFragment.SwipeBack {

    override val fragmentName: String = "ThemeScreen"

    override val viewModel: ThemeViewModel by viewModel()

	private lateinit var adapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(Localization.appearance))
		adapter = Adapter(
			onClickTheme = { item -> viewModel.setTheme(item.theme.key) },
			onWallpaperColorsChanged = viewModel::setWallpaperColors,
			onMaterialYouAmoledChanged = viewModel::setAmoled,
			onMaterialYouAction = ::onMaterialYouAction,
			onPresetSelected = viewModel::setPreset,
		)

        setAdapter(adapter)
        addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val item = adapter.getItem(position)
				if (item is Item.Icon) {
					val previousItem = if (position > 0) adapter.getItem(position - 1) else null
					if (previousItem !is Item.Icon) {
                        outRect.set(0, 0, 0, 0)
                    } else {
                        outRect.set(6.dp, 0, 0, 0)
                    }
                } else {
                    outRect.set(0, 0, 0, 0)
                }
            }
        })
        setLayoutManager(object : GridLayoutManager(context, 4) {
            init {
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (adapter.getItemViewType(position)) {
                            Item.TYPE_ICON -> 1
                            else -> 4
                        }
                    }
                }
            }

            override fun supportsPredictiveItemAnimations(): Boolean = false
        })

		collectFlow(viewModel.uiItemsFlow, adapter::submitList)
	}

	private fun onMaterialYouAction(action: Item.MaterialYouAction.Action) {
		when (action) {
			Item.MaterialYouAction.Action.GENERATOR -> showGeneratorPicker()
			Item.MaterialYouAction.Action.CUSTOM_COLOR -> showColorPicker()
		}
	}

	private fun showColorPicker() {
		navigation?.addForResult(
			MaterialYouColorPickerScreen.newInstance(viewModel.materialYouSettings.customColor),
		) { result ->
			if (result.containsKey(MaterialYouColorPickerScreen.RESULT_COLOR)) {
				viewModel.setCustomColor(
					result.getInt(MaterialYouColorPickerScreen.RESULT_COLOR),
				)
			}
		}
	}

	private fun showGeneratorPicker() {
		val generators = MaterialYouGenerator.entries
		val selected = generators.indexOf(viewModel.materialYouSettings.generator)
		AlertDialog.Builder(requireContext())
			.setTitle(getString(Localization.color_generator))
			.setSingleChoiceItems(
				generators.map(viewModel::generatorTitle).toTypedArray(),
				selected,
			) { dialog, index ->
				viewModel.setGenerator(generators[index])
				dialog.dismiss()
			}
			.setNegativeButton(getString(Localization.cancel), null)
			.show()
	}

    companion object {
        fun newInstance(wallet: WalletEntity) = ThemeScreen(wallet)
    }
}
