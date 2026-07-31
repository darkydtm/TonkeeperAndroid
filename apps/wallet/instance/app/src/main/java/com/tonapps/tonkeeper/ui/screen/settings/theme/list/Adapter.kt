package com.tonapps.tonkeeper.ui.screen.settings.theme.list

import android.view.ViewGroup
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.FontSizeHolder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.Holder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.IconHolder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.MaterialYouAmoledHolder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.MaterialYouActionHolder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.MaterialYouPresetsHolder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.SpaceHolder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.ThemeHolder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.TitleHolder
import com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder.WallpaperColorsHolder
import com.tonapps.uikit.list.BaseListAdapter
import com.tonapps.uikit.list.BaseListHolder
import com.tonapps.uikit.list.BaseListItem

class Adapter(
	private val onClickTheme: (item: Item.Theme) -> Unit,
	private val onWallpaperColorsChanged: (enabled: Boolean) -> Unit,
	private val onMaterialYouAmoledChanged: (enabled: Boolean) -> Unit,
	private val onMaterialYouAction: (action: Item.MaterialYouAction.Action) -> Unit,
	private val onPresetSelected: (preset: com.tonapps.wallet.data.core.MaterialYouPreset) -> Unit,
): BaseListAdapter() {

    override fun createHolder(parent: ViewGroup, viewType: Int): BaseListHolder<out BaseListItem> {
        return when (viewType) {
            Item.TYPE_THEME -> ThemeHolder(parent, onClickTheme)
            Item.TYPE_TITLE -> TitleHolder(parent)
            Item.TYPE_ICON -> IconHolder(parent)
            Item.TYPE_SPACE -> SpaceHolder(parent)
			Item.TYPE_FONT_SIZE -> FontSizeHolder(parent)
			Item.TYPE_WALLPAPER_COLORS -> WallpaperColorsHolder(parent, onWallpaperColorsChanged)
			Item.TYPE_MATERIAL_YOU_AMOLED -> MaterialYouAmoledHolder(parent, onMaterialYouAmoledChanged)
			Item.TYPE_MATERIAL_YOU_ACTION -> MaterialYouActionHolder(parent, onMaterialYouAction)
			Item.TYPE_MATERIAL_YOU_PRESETS -> MaterialYouPresetsHolder(parent, onPresetSelected)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }
}
