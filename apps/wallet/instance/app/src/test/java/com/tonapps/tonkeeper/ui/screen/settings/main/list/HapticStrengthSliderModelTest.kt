package com.tonapps.tonkeeper.ui.screen.settings.main.list

import org.junit.Assert.assertEquals
import org.junit.Test

class HapticStrengthSliderModelTest {

	@Test
	fun `ordinary values change after one step`() {
		val model = HapticStrengthSliderModel(0.6f)

		assertEquals(0.7f, model.dragBySteps(1f))
	}

	@Test
	fun `half steps require extra drag distance`() {
		val model = HapticStrengthSliderModel(0.5f)

		assertEquals(0.5f, model.dragBySteps(1.4f))
		assertEquals(0.6f, model.dragBySteps(0.1f))
	}

	@Test
	fun `dragging remains inside supported range`() {
		val model = HapticStrengthSliderModel(3f)

		assertEquals(3f, model.dragBySteps(10f))
		model.setValue(0f)
		assertEquals(0f, model.dragBySteps(-10f))
	}

	@Test
	fun `percent uses coefficient scale`() {
		assertEquals("0%", formatHapticStrengthPercent(0f))
		assertEquals("100%", formatHapticStrengthPercent(1f))
		assertEquals("300%", formatHapticStrengthPercent(3f))
	}
}
