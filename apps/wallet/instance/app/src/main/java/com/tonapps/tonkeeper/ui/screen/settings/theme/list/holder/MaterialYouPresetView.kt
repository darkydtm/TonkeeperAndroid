package com.tonapps.tonkeeper.ui.screen.settings.theme.list.holder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.tonapps.uikit.color.backgroundContentColor
import com.tonapps.uikit.color.textPrimaryColor
import com.tonapps.wallet.data.core.MaterialYouPreset
import uikit.extensions.dp

class MaterialYouPresetView(context: Context) : View(context) {
	private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
	private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
		style = Paint.Style.STROKE
		strokeWidth = 2f.dp
	}
	private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
		style = Paint.Style.STROKE
		strokeWidth = 2f.dp
		color = context.backgroundContentColor
	}
	init {
		minimumWidth = 48.dp
		minimumHeight = 48.dp
		isClickable = true
		isFocusable = true
	}

	fun bind(
		preset: MaterialYouPreset,
		title: String,
		selected: Boolean,
		onClick: (MaterialYouPreset) -> Unit,
	) {
		fillPaint.color = preset.color
		outerPaint.color = context.textPrimaryColor
		isSelected = selected
		contentDescription = title
		setOnClickListener { onClick(preset) }
		invalidate()
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		val centerX = width / 2f
		val centerY = height / 2f
		val radius = 16f.dp
		canvas.drawCircle(centerX, centerY, radius, fillPaint)
		if (isSelected) {
			canvas.drawCircle(centerX, centerY, radius + 3f.dp, innerPaint)
			canvas.drawCircle(centerX, centerY, radius + 5f.dp, outerPaint)
		}
	}
}
