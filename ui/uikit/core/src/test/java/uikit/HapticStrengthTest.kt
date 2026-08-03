package uikit

import org.junit.Assert.assertEquals
import org.junit.Test

class HapticStrengthTest {

	@Test
	fun `normalize rounds to tenths and clamps range`() {
		assertEquals(0f, HapticStrength.normalize(-1f))
		assertEquals(1.3f, HapticStrength.normalize(1.26f))
		assertEquals(3f, HapticStrength.normalize(4f))
		assertEquals(1f, HapticStrength.normalize(Float.NaN))
	}

	@Test
	fun `scale amplitude uses coefficient and caps Android range`() {
		assertEquals(0, HapticStrength.scaleAmplitude(85, 0f))
		assertEquals(85, HapticStrength.scaleAmplitude(85, 1f))
		assertEquals(255, HapticStrength.scaleAmplitude(85, 3f))
		assertEquals(255, HapticStrength.scaleAmplitude(180, 2f))
	}
}
