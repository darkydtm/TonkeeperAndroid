package uikit.widget

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class BottomSheetHeightTest {
	@Test
	fun `full ratio keeps available height`() {
		assertEquals(800, calculateBottomSheetHeight(800, 1f))
	}

	@Test
	fun `half ratio halves available height`() {
		assertEquals(400, calculateBottomSheetHeight(800, 0.5f))
	}

	@Test
	fun `invalid ratio is rejected`() {
		assertThrows(IllegalArgumentException::class.java) {
			calculateBottomSheetHeight(800, 0f)
		}
		assertThrows(IllegalArgumentException::class.java) {
			calculateBottomSheetHeight(800, 1.1f)
		}
	}
}
