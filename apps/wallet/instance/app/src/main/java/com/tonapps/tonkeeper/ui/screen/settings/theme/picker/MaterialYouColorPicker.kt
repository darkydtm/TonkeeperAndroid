package com.tonapps.tonkeeper.ui.screen.settings.theme.picker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.tonapps.wallet.localization.Localization

@Composable
fun MaterialYouColorPicker(
	initialColor: Int,
	onCancel: () -> Unit,
	onApply: (Int) -> Unit,
) {
	val context = LocalContext.current
	var pickerState by remember(initialColor) { mutableStateOf(ColorPickerState.fromColor(initialColor)) }
	var hexValue by remember(initialColor) { mutableStateOf(pickerState.hex) }
	var invalidHex by remember { mutableStateOf(false) }

	fun updateColor(updatedState: ColorPickerState) {
		pickerState = updatedState
		hexValue = updatedState.hex
		invalidHex = false
	}

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.background(MaterialTheme.colorScheme.surface)
			.navigationBarsPadding()
			.imePadding()
			.verticalScroll(rememberScrollState())
			.padding(horizontal = 24.dp, vertical = 20.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		Text(
			text = context.getString(Localization.select_custom_color),
			style = MaterialTheme.typography.titleLarge,
			color = MaterialTheme.colorScheme.onSurface,
		)
		BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
			val saturationValueSize = (maxWidth - 64.dp).coerceAtMost(248.dp)
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center,
			) {
				SaturationValuePicker(
					state = pickerState,
					onStateChanged = ::updateColor,
					modifier = Modifier.size(saturationValueSize),
				)
				Spacer(modifier = Modifier.width(16.dp))
				HuePicker(
					state = pickerState,
					onStateChanged = ::updateColor,
					modifier = Modifier
						.width(48.dp)
						.height(saturationValueSize),
				)
			}
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(12.dp),
		) {
			Box(
				modifier = Modifier
					.padding(top = 8.dp)
					.size(48.dp)
					.background(Color(pickerState.color), CircleShape)
					.semantics {
						contentDescription = context.getString(Localization.color_preview)
					},
			)
			OutlinedTextField(
				value = hexValue,
				onValueChange = { value ->
					val normalized = value.uppercase().take(7)
					hexValue = normalized
					val parsed = ColorPickerState.fromHex(normalized)
					invalidHex = parsed == null
					if (parsed != null) {
						pickerState = parsed
					}
				},
				modifier = Modifier.weight(1f),
				label = { Text(context.getString(Localization.hex_color)) },
				singleLine = true,
				isError = invalidHex,
				supportingText = if (invalidHex) {
					{ Text(context.getString(Localization.invalid_hex_color)) }
				} else {
					null
				},
				keyboardOptions = KeyboardOptions(
					capitalization = KeyboardCapitalization.Characters,
					keyboardType = KeyboardType.Ascii,
				),
			)
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.End,
		) {
			TextButton(onClick = onCancel) {
				Text(context.getString(Localization.cancel))
			}
			Spacer(modifier = Modifier.width(8.dp))
			Button(
				onClick = {
					val parsed = ColorPickerState.fromHex(hexValue)
					if (parsed == null) {
						invalidHex = true
					} else {
						onApply(parsed.color)
					}
				},
			) {
				Text(context.getString(Localization.apply))
			}
		}
	}
}

@Composable
private fun SaturationValuePicker(
	state: ColorPickerState,
	onStateChanged: (ColorPickerState) -> Unit,
	modifier: Modifier,
) {
	val context = LocalContext.current
	val currentState by rememberUpdatedState(state)
	var size by remember { mutableStateOf(IntSize.Zero) }
	Canvas(
		modifier = modifier
			.onSizeChanged { size = it }
			.semantics {
				contentDescription = context.getString(Localization.saturation_value)
			}
			.pointerInput(size) {
				detectTapGestures { offset ->
					onStateChanged(
						currentState.withSaturationValue(
							x = offset.x,
							y = offset.y,
							width = size.width.toFloat(),
							height = size.height.toFloat(),
						),
					)
				}
			}
			.pointerInput(size) {
				detectDragGestures { change, _ ->
					onStateChanged(
						currentState.withSaturationValue(
							x = change.position.x,
							y = change.position.y,
							width = size.width.toFloat(),
							height = size.height.toFloat(),
						),
					)
				}
			},
	) {
		val markerRadius = 12.dp.toPx()
		drawRect(
			brush = Brush.horizontalGradient(
				listOf(Color.White, Color.hsv(state.hue, 1f, 1f)),
			),
		)
		drawRect(
			brush = Brush.verticalGradient(
				listOf(Color.Transparent, Color.Black),
			),
		)
		val marker = Offset(
			x = (state.saturation * this.size.width).coerceIn(
				markerRadius,
				this.size.width - markerRadius,
			),
			y = ((1f - state.value) * this.size.height).coerceIn(
				markerRadius,
				this.size.height - markerRadius,
			),
		)
		drawMarker(marker)
	}
}

@Composable
private fun HuePicker(
	state: ColorPickerState,
	onStateChanged: (ColorPickerState) -> Unit,
	modifier: Modifier,
) {
	val context = LocalContext.current
	val currentState by rememberUpdatedState(state)
	var size by remember { mutableStateOf(IntSize.Zero) }
	Canvas(
		modifier = modifier
			.onSizeChanged { size = it }
			.semantics {
				contentDescription = context.getString(Localization.hue)
			}
			.pointerInput(size) {
				detectTapGestures { offset ->
					onStateChanged(currentState.withHue(offset.y, size.height.toFloat()))
				}
			}
			.pointerInput(size) {
				detectDragGestures { change, _ ->
					onStateChanged(currentState.withHue(change.position.y, size.height.toFloat()))
				}
			},
	) {
		val markerRadius = 12.dp.toPx()
		drawRect(
			brush = Brush.verticalGradient(
				listOf(
					Color.Red,
					Color.Yellow,
					Color.Green,
					Color.Cyan,
					Color.Blue,
					Color.Magenta,
					Color.Red,
				),
			),
		)
		drawMarker(
			Offset(
				x = this.size.width / 2f,
				y = (state.hue / 360f * this.size.height).coerceIn(
					markerRadius,
					this.size.height - markerRadius,
				),
			),
		)
	}
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawMarker(center: Offset) {
	drawCircle(
		color = Color.Black.copy(alpha = 0.7f),
		radius = 12.dp.toPx(),
		center = center,
		style = Stroke(width = 3.dp.toPx()),
	)
	drawCircle(
		color = Color.White,
		radius = 9.dp.toPx(),
		center = center,
		style = Stroke(width = 3.dp.toPx()),
	)
}
