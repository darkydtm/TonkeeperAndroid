package uikit

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings

object HapticHelper {

	fun selection(context: Context) {
		perform(context, HapticType.SELECTION)
	}

	fun error(context: Context) {
		perform(context, HapticType.ERROR)
	}

	fun warning(context: Context) {
		perform(context, HapticType.WARNING)
	}

	fun success(context: Context) {
		perform(context, HapticType.SUCCESS)
	}

	fun impactLight(context: Context) {
		perform(context, HapticType.LIGHT)
	}

	fun confirm(context: Context) {
		perform(context, HapticType.CONFIRM)
	}

	fun perform(context: Context, type: HapticType) {
		if (!isEnabled(context)) {
			return
		}

		val vibrator = getVibrator(context) ?: return
		if (!vibrator.hasVibrator()) {
			return
		}

		val effect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			VibrationEffect.createPredefined(type.predefinedEffect())
		} else {
			VibrationEffect.createWaveform(type.pattern(), -1)
		}
		vibrator.vibrate(effect)
	}

	private fun isEnabled(context: Context): Boolean {
		return try {
			Settings.System.getInt(
				context.contentResolver,
				Settings.System.HAPTIC_FEEDBACK_ENABLED,
				0,
			) == 1
		} catch (_: SecurityException) {
			false
		}
	}

	private fun getVibrator(context: Context): Vibrator? {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			(context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)
				?.defaultVibrator
		} else {
			context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
		}
	}

	private fun HapticType.predefinedEffect(): Int = when (this) {
		HapticType.LIGHT -> VibrationEffect.EFFECT_CLICK
		HapticType.SELECTION -> VibrationEffect.EFFECT_TICK
		HapticType.CONFIRM -> VibrationEffect.EFFECT_HEAVY_CLICK
		HapticType.SUCCESS -> VibrationEffect.EFFECT_DOUBLE_CLICK
		HapticType.WARNING -> VibrationEffect.EFFECT_DOUBLE_CLICK
		HapticType.ERROR -> VibrationEffect.EFFECT_DOUBLE_CLICK
	}

	private fun HapticType.pattern(): LongArray = when (this) {
		HapticType.LIGHT -> longArrayOf(0, 10)
		HapticType.SELECTION -> longArrayOf(0, 15)
		HapticType.CONFIRM -> longArrayOf(0, 30)
		HapticType.SUCCESS -> longArrayOf(0, 30, 50, 10)
		HapticType.WARNING -> longArrayOf(0, 10, 50, 30)
		HapticType.ERROR -> longArrayOf(0, 10, 30, 20, 30, 30)
	}
}
