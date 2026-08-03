package ui.components.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.util.fastForEachIndexed
import ui.theme.Dimens
import ui.theme.Shapes.shape
import ui.theme.UIKit
import ui.theme.modifiers.bottomDivider
import ui.haptic.hapticClickable

sealed class EventItemClickPart {
    data class Action(val index: Int): EventItemClickPart()
    data class Product(val index: Int): EventItemClickPart()
    data class Encrypted(val index: Int): EventItemClickPart()
}

@Composable
fun EventItem(
    event: UiEvent.Item,
    hiddenBalances: Boolean,
    onClick: (id: String, part: EventItemClickPart) -> Unit
) {
    event.actions.fastForEachIndexed { index, action ->
        EventAction(
            modifier = Modifier
                .clip(action.position.shape())
                .background(UIKit.colorScheme.background.content)
                .bottomDivider(action.showDivider)
                .hapticClickable(onClick = {
                    onClick(event.id, EventItemClickPart.Action(index))
                })
                .padding(bottom = Dimens.offsetMedium),
            action = action,
            index = index,
            hiddenBalances = hiddenBalances,
            onClick = {
                onClick(event.id, it)
            }
        )
    }
}
