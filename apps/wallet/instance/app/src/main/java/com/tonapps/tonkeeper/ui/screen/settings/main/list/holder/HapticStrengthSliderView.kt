package com.tonapps.tonkeeper.ui.screen.settings.main.list.holder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.tonapps.tonkeeper.ui.screen.settings.main.list.HapticStrengthSliderModel
import com.tonapps.uikit.color.accentBlueColor
import com.tonapps.uikit.color.separatorAlternateColor
import uikit.HapticStrength
import uikit.extensions.dp
import kotlin.math.abs
import kotlin.math.roundToInt

class HapticStrengthSliderView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

	var onValueChanged: ((Float) -> Unit)? = null
	var onValueCommitted: ((Float) -> Unit)? = null

	private var currentValue = HapticStrength.DEFAULT
	private val model = HapticStrengthSliderModel(currentValue)

	var value: Float
		get() = currentValue
		set(value) {
			updateValue(value, notify = false)
		}

	private val activePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
		color = context.accentBlueColor
	}
	private val inactivePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
		color = context.separatorAlternateColor
	}
	private val trackHeight = 4.dp.toFloat()
	private val trackRadius = trackHeight / 2f
	private val dotRadius = 1.dp.toFloat()
	private val thumbWidth = 3.dp.toFloat()
	private val thumbHeight = 28.dp.toFloat()
	private var lastTouchX = 0f
	private var dragStartValue = value

	private val trackStart: Float
		get() = paddingLeft + thumbWidth / 2f

	private val trackEnd: Float
		get() = width - paddingRight - thumbWidth / 2f

	private val trackWidth: Float
		get() = (trackEnd - trackStart).coerceAtLeast(0f)

	private val thumbX: Float
		get() = trackStart + trackWidth * value / HapticStrength.MAX

	init {
		isClickable = true
		importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val desiredHeight = 48.dp + paddingTop + paddingBottom
		setMeasuredDimension(
			resolveSize(suggestedMinimumWidth, widthMeasureSpec),
			resolveSize(desiredHeight, heightMeasureSpec),
		)
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		val centerY = height / 2f
		canvas.drawRoundRect(
			trackStart,
			centerY - trackRadius,
			trackEnd,
			centerY + trackRadius,
			trackRadius,
			trackRadius,
			inactivePaint,
		)
		canvas.drawRoundRect(
			trackStart,
			centerY - trackRadius,
			thumbX,
			centerY + trackRadius,
			trackRadius,
			trackRadius,
			activePaint,
		)

		val selectedStep = (value / HapticStrength.STEP).roundToInt()
		for (step in 0..STEPS) {
			val x = trackStart + trackWidth * step / STEPS
			canvas.drawCircle(
				x,
				centerY,
				dotRadius,
				if (step <= selectedStep) activePaint else inactivePaint,
			)
		}

		canvas.drawRoundRect(
			thumbX - thumbWidth / 2f,
			centerY - thumbHeight / 2f,
			thumbX + thumbWidth / 2f,
			centerY + thumbHeight / 2f,
			thumbWidth / 2f,
			thumbWidth / 2f,
			activePaint,
		)
	}

	override fun onTouchEvent(event: MotionEvent): Boolean {
		if (!isEnabled || trackWidth == 0f) {
			return false
		}
		when (event.actionMasked) {
			MotionEvent.ACTION_DOWN -> startDrag(event.x)
			MotionEvent.ACTION_MOVE -> dragTo(event.x)
			MotionEvent.ACTION_UP -> finishDrag(event.x)
			MotionEvent.ACTION_CANCEL -> cancelDrag()
		}
		return true
	}

	override fun performClick(): Boolean {
		super.performClick()
		return true
	}

	override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
		super.onInitializeAccessibilityNodeInfo(info)
		info.className = "android.widget.SeekBar"
		info.rangeInfo = AccessibilityNodeInfo.RangeInfo.obtain(
			AccessibilityNodeInfo.RangeInfo.RANGE_TYPE_FLOAT,
			HapticStrength.MIN,
			HapticStrength.MAX,
			value,
		)
		info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS)
		info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD)
		info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD)
	}

	override fun performAccessibilityAction(action: Int, arguments: Bundle?): Boolean {
		val nextValue = when (action) {
			AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS.id -> arguments?.getFloat(
				AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE,
			)
			AccessibilityNodeInfo.ACTION_SCROLL_FORWARD -> value + HapticStrength.STEP
			AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD -> value - HapticStrength.STEP
			else -> return super.performAccessibilityAction(action, arguments)
		} ?: return false
		updateValue(nextValue)
		onValueCommitted?.invoke(value)
		return true
	}

	private fun startDrag(x: Float) {
		parent.requestDisallowInterceptTouchEvent(true)
		dragStartValue = value
		model.setValue(value)
		lastTouchX = x
		if (abs(x - thumbX) > 24.dp) {
			updateValue(valueFromX(x))
		}
	}

	private fun dragTo(x: Float) {
		val stepWidth = trackWidth / STEPS
		if (stepWidth == 0f) {
			return
		}
		val nextValue = model.dragBySteps((x - lastTouchX) / stepWidth)
		lastTouchX = x
		updateValue(nextValue, updateModel = false)
	}

	private fun finishDrag(x: Float) {
		dragTo(x)
		parent.requestDisallowInterceptTouchEvent(false)
		onValueCommitted?.invoke(value)
		performClick()
	}

	private fun cancelDrag() {
		parent.requestDisallowInterceptTouchEvent(false)
		updateValue(dragStartValue)
	}

	private fun valueFromX(x: Float): Float {
		return HapticStrength.MAX * ((x - trackStart) / trackWidth).coerceIn(0f, 1f)
	}

	private fun updateValue(
		value: Float,
		updateModel: Boolean = true,
		notify: Boolean = true,
	) {
		val normalized = HapticStrength.normalize(value)
		if (currentValue == normalized) {
			return
		}
		currentValue = normalized
		if (updateModel) {
			model.setValue(normalized)
		}
		invalidate()
		if (notify) {
			onValueChanged?.invoke(normalized)
		}
	}

	private companion object {
		const val STEPS = 30
	}
}
