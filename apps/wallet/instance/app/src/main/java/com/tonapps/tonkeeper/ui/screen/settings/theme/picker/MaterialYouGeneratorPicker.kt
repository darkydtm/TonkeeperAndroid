package com.tonapps.tonkeeper.ui.screen.settings.theme.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import com.tonapps.tonkeeper.ui.screen.settings.theme.titleRes
import com.tonapps.wallet.data.core.MaterialYouGenerator
import com.tonapps.wallet.localization.Localization
import ui.haptic.hapticSelectable

@Composable
fun MaterialYouGeneratorPicker(
	selectedGenerator: MaterialYouGenerator,
	onGeneratorSelected: (MaterialYouGenerator) -> Unit,
) {
	val context = LocalContext.current
	val nestedScrollInterop = rememberNestedScrollInteropConnection()
	Column(
		modifier = Modifier
			.fillMaxSize()
			.navigationBarsPadding()
			.padding(horizontal = 24.dp, vertical = 20.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		Text(
			text = context.getString(Localization.color_generator),
			style = MaterialTheme.typography.titleLarge,
			color = MaterialTheme.colorScheme.onSurface,
		)
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth()
				.nestedScroll(nestedScrollInterop)
				.weight(1f),
			verticalArrangement = Arrangement.spacedBy(8.dp),
		) {
			items(
				items = MaterialYouGenerator.entries,
				key = MaterialYouGenerator::storageKey,
			) { generator ->
				GeneratorRow(
					generator = generator,
					selected = generator == selectedGenerator,
					onClick = { onGeneratorSelected(generator) },
				)
			}
		}
	}
}

@Composable
private fun GeneratorRow(
	generator: MaterialYouGenerator,
	selected: Boolean,
	onClick: () -> Unit,
) {
	val colors = MaterialTheme.colorScheme
	val shape = MaterialTheme.shapes.large
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.clip(shape)
			.hapticSelectable(
				selected = selected,
				onClick = onClick,
			),
		shape = shape,
		color = if (selected) colors.primaryContainer else colors.surfaceContainerLow,
	) {
		Row(
			modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(12.dp),
		) {
			Text(
				text = LocalContext.current.getString(generator.titleRes()),
				modifier = Modifier.weight(1f),
				style = MaterialTheme.typography.bodyLarge,
				color = if (selected) colors.onPrimaryContainer else colors.onSurface,
			)
			if (selected) {
				Text(
					text = "✓",
					style = MaterialTheme.typography.titleLarge,
					color = colors.onPrimaryContainer,
				)
			}
		}
	}
}
