package uikit.widget

import org.junit.Assert.assertEquals
import org.junit.Test

class PredictiveBackTranslationTest {
	@Test
	fun `zero progress keeps content in place`() {
		assertEquals(0f, calculatePredictiveBackTranslation(1000, 400, 0f))
	}

	@Test
	fun `partial progress moves content toward bottom edge`() {
		assertEquals(300f, calculatePredictiveBackTranslation(1000, 400, 0.5f))
	}

	@Test
	fun `complete progress moves content beyond bottom edge`() {
		assertEquals(600f, calculatePredictiveBackTranslation(1000, 400, 1f))
	}

	@Test
	fun `progress is clamped`() {
		assertEquals(0f, calculatePredictiveBackTranslation(1000, 400, -1f))
		assertEquals(600f, calculatePredictiveBackTranslation(1000, 400, 2f))
	}

	@Test
	fun `content below container has no remaining distance`() {
		assertEquals(0f, calculatePredictiveBackTranslation(1000, 1200, 1f))
	}
}
