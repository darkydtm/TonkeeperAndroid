package com.tonapps.wallet.data.core

enum class MaterialYouGenerator(val storageKey: String) {
	TONAL_SPOT("tonal_spot"),
	VIBRANT("vibrant"),
	EXPRESSIVE("expressive"),
	NEUTRAL("neutral"),
	MONOCHROME("monochrome"),
	RAINBOW("rainbow"),
	FRUIT_SALAD("fruit_salad"),
	FIDELITY("fidelity"),
	CONTENT("content");

	companion object {
		fun fromStorageKey(value: String?): MaterialYouGenerator {
			return entries.firstOrNull { it.storageKey == value } ?: TONAL_SPOT
		}
	}
}

enum class MaterialYouPreset(
	val storageKey: String,
	val color: Int,
) {
	RED("red", 0xFFF44336.toInt()),
	ORANGE("orange", 0xFFFF9800.toInt()),
	YELLOW("yellow", 0xFFFFEB3B.toInt()),
	GREEN("green", 0xFF4CAF50.toInt()),
	CYAN("cyan", 0xFF00BCD4.toInt()),
	BLUE("blue", 0xFF2196F3.toInt()),
	PURPLE("purple", 0xFF9C27B0.toInt()),
	PINK("pink", 0xFFE91E63.toInt());

	companion object {
		fun fromStorageKey(value: String?): MaterialYouPreset {
			return entries.firstOrNull { it.storageKey == value } ?: BLUE
		}
	}
}

enum class MaterialYouColorSource(val storageKey: String) {
	PRESET("preset"),
	CUSTOM("custom");

	companion object {
		fun fromStorageKey(value: String?): MaterialYouColorSource {
			return entries.firstOrNull { it.storageKey == value } ?: PRESET
		}
	}
}

data class MaterialYouSettings(
	val useWallpaperColors: Boolean = true,
	val generator: MaterialYouGenerator = MaterialYouGenerator.TONAL_SPOT,
	val colorSource: MaterialYouColorSource = MaterialYouColorSource.PRESET,
	val preset: MaterialYouPreset = MaterialYouPreset.BLUE,
	val customColor: Int = MaterialYouPreset.BLUE.color,
)

object MaterialYouHexColor {
	private val pattern = Regex("^#[0-9A-Fa-f]{6}$")

	fun parse(value: String?): Int? {
		if (value == null || !pattern.matches(value)) {
			return null
		}
		return (0xFF000000L or value.substring(1).toLong(16)).toInt()
	}

	fun format(color: Int): String {
		return "#%06X".format(color and 0x00FFFFFF)
	}
}
