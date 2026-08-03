package com.tonapps.tonkeeper.ui.screen.wallet.main.list.holder

import uikit.extensions.setHapticClickListener

import android.view.ViewGroup
import android.widget.Button
import com.tonapps.tonkeeper.ui.screen.wallet.main.list.Item
import com.tonapps.tonkeeper.ui.screen.wallet.manage.TokensManageScreen
import com.tonapps.tonkeeperx.R
import uikit.navigation.Navigation

class ManageHolder(
    parent: ViewGroup
): Holder<Item.Manage>(parent, R.layout.view_wallet_manage) {

    private val button = findViewById<Button>(R.id.button)

    override fun onBind(item: Item.Manage) {
        button.setHapticClickListener {
            Navigation.from(context)?.add(TokensManageScreen.newInstance(item.wallet))
        }
    }

}