package com.example.model

import androidx.annotation.DrawableRes

enum class WallpaperCategory(
    val id: String,
    val displayName: String,
    val description: String,
    val gradientColors: List<Long>
) {
    BATTLE_ROYALE(
        id = "battle_royale",
        displayName = "Battle Royale",
        description = "Warriors, futuristic combat & tactical drops",
        gradientColors = listOf(0xFFFF0055, 0xFFFF7700)
    ),
    CARS(
        id = "cars",
        displayName = "Cars",
        description = "Cyber hypercars, drift beasts & neon speeds",
        gradientColors = listOf(0xFF00F0FF, 0xFF0051FF)
    ),
    ANIME(
        id = "anime",
        displayName = "Anime",
        description = "Cyber mechas, anime pilots & fantasy vistas",
        gradientColors = listOf(0xFFFF007A, 0xFF7928CA)
    ),
    NATURE(
        id = "nature",
        displayName = "Nature",
        description = "Mystic aurora, obsidian mountains & alien biomes",
        gradientColors = listOf(0xFF00F5A0, 0xFF00A86B)
    ),
    NEON(
        id = "neon",
        displayName = "Neon",
        description = "Ultraviolet glows, cyber cities & laser trails",
        gradientColors = listOf(0xFF9D4EDD, 0xFF00F0FF)
    ),
    ABSTRACT(
        id = "abstract",
        displayName = "Abstract",
        description = "3D geometric prisms, dark poly & cyber vortexes",
        gradientColors = listOf(0xFF7928CA, 0xFFFF0055)
    );

    companion object {
        fun fromId(id: String): WallpaperCategory =
            entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: BATTLE_ROYALE
    }
}

data class Wallpaper(
    val id: String,
    val title: String,
    val category: WallpaperCategory,
    @DrawableRes val localDrawableId: Int? = null,
    val remoteUrl: String? = null,
    val resolution: String = "4K Ultra HD (3840×2160)",
    val author: String = "Pro Art Studio",
    val tags: List<String> = emptyList(),
    val downloads: Int = 1200,
    val likes: Int = 450,
    val isFeatured: Boolean = false,
    val isOled: Boolean = true,
    val dominantColorHex: Long = 0xFF00F0FF
)
