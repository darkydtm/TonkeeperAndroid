package uikit

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings

object HapticHelper {

	var strength: Float = HapticStrength.DEFAULT
		set(value) {
			field = HapticStrength.normalize(value)
		}

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

	fun perform(context: Context, type: HapticType): Boolean {
		if (strength == HapticStrength.MIN || !isEnabled(context)) {
			return false
		}

		val vibrator = getVibrator(context) ?: return false
		if (!vibrator.hasVibrator()) {
			return false
		}

		val effect = if (vibrator.hasAmplitudeControl()) {
			VibrationEffect.createWaveform(
				type.pattern(),
				type.amplitudes(strength),
				-1,
			)
		} else {
			VibrationEffect.createWaveform(type.pattern(), -1)
		}
		vibrator.vibrate(effect)
		return true
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

	private fun HapticType.pattern(): LongArray = when (this) {
		HapticType.LIGHT -> longArrayOf(0, 10)
		HapticType.SELECTION -> longArrayOf(0, 15)
		HapticType.CONFIRM -> longArrayOf(0, 30)
		HapticType.SUCCESS -> longArrayOf(0, 30, 50, 10)
		HapticType.WARNING -> longArrayOf(0, 10, 50, 30)
		HapticType.ERROR -> longArrayOf(0, 10, 30, 20, 30, 30)
	}

	private fun HapticType.amplitudes(strength: Float): IntArray {
		return baseAmplitudes().map { amplitude ->
			HapticStrength.scaleAmplitude(amplitude, strength)
		}.toIntArray()
	}

	private fun HapticType.baseAmplitudes(): IntArray = when (this) {
		HapticType.LIGHT -> intArrayOf(0, 85)
		HapticType.SELECTION -> intArrayOf(0, 65)
		HapticType.CONFIRM -> intArrayOf(0, 120)
		HapticType.SUCCESS -> intArrayOf(0, 100, 0, 85)
		HapticType.WARNING -> intArrayOf(0, 80, 0, 120)
		HapticType.ERROR -> intArrayOf(0, 90, 0, 110, 0, 140)
	}
}
