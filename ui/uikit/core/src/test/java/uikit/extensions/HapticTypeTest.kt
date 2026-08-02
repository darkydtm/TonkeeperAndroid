package uikit.extensions

import android.os.Build
import android.view.HapticFeedbackConstants
import org.junit.Assert.assertEquals
import org.junit.Test
import uikit.HapticType

class HapticTypeTest {

	@Test
	fun `map light feedback to keyboard tap`() {
		assertEquals(
			HapticFeedbackConstants.KEYBOARD_TAP,
			HapticType.LIGHT.feedbackConstant(Build.VERSION_CODES.O),
		)
	}

	@Test
	fun `map selection feedback to clock tick`() {
		assertEquals(
			HapticFeedbackConstants.CLOCK_TICK,
			HapticType.SELECTION.feedbackConstant(Build.VERSION_CODES.O),
		)
	}

	@Test
	fun `map confirmation feedback to compatible constants`() {
		assertEquals(
			HapticFeedbackConstants.CONTEXT_CLICK,
			HapticType.CONFIRM.feedbackConstant(Build.VERSION_CODES.Q),
		)
		assertEquals(
			HapticFeedbackConstants.CONFIRM,
			HapticType.CONFIRM.feedbackConstant(Build.VERSION_CODES.R),
		)
	}

	@Test
	fun `map result feedback to compatible constants`() {
		assertEquals(
			HapticFeedbackConstants.CONFIRM,
			HapticType.SUCCESS.feedbackConstant(Build.VERSION_CODES.R),
		)
		assertEquals(
			HapticFeedbackConstants.LONG_PRESS,
			HapticType.ERROR.feedbackConstant(Build.VERSION_CODES.Q),
		)
		assertEquals(
			HapticFeedbackConstants.REJECT,
			HapticType.WARNING.feedbackConstant(Build.VERSION_CODES.R),
		)
	}
}
