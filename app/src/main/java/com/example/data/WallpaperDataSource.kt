package com.example.data

import com.example.R
import com.example.model.Wallpaper
import com.example.model.WallpaperCategory

object WallpaperDataSource {

    val sampleWallpapers: List<Wallpaper> = listOf(
        // Generated bundled local wallpapers (instant load, 0 network delay, 100% reliable)
        Wallpaper(
            id = "wp_battle_1",
            title = "Cyber Ronin",
            category = WallpaperCategory.BATTLE_ROYALE,
            localDrawableId = R.drawable.img_cyber_warrior,
            resolution = "4K UHD (3840×2160)",
            author = "Apex Studios",
            tags = listOf("Cyberpunk", "Warrior", "Katana", "Rooftop", "4K"),
            downloads = 34500,
            likes = 8900,
            isFeatured = true,
            isOled = true,
            dominantColorHex = 0xFF00F0FF
        ),
        Wallpaper(
            id = "wp_cars_1",
            title = "Apex Hypercar",
            category = WallpaperCategory.CARS,
            localDrawableId = R.drawable.img_neon_hypercar,
            resolution = "4K UHD (3840×2160)",
            author = "Velocity Lab",
            tags = listOf("Supercar", "Neon Drift", "Speed", "Night", "Cyber"),
            downloads = 42100,
            likes = 11200,
            isFeatured = true,
            isOled = true,
            dominantColorHex = 0xFF9D4EDD
        ),
        Wallpaper(
            id = "wp_anime_1",
            title = "Aether Valkyrie",
            category = WallpaperCategory.ANIME,
            localDrawableId = R.drawable.img_anime_pilot,
            resolution = "4K UHD (3840×2160)",
            author = "NeoTokyo Art",
            tags = listOf("Anime", "Mecha Pilot", "Valkyrie", "Sci-Fi", "Glow"),
            downloads = 28900,
            likes = 7400,
            isFeatured = true,
            isOled = false,
            dominantColorHex = 0xFFFF007A
        ),
        Wallpaper(
            id = "wp_abstract_1",
            title = "Quantum Monolith",
            category = WallpaperCategory.ABSTRACT,
            localDrawableId = R.drawable.img_neon_abstract,
            resolution = "4K UHD (3840×2160)",
            author = "Prism Digital",
            tags = listOf("3D Crystal", "Monolith", "Geometry", "Laser", "Abstract"),
            downloads = 31200,
            likes = 6800,
            isFeatured = true,
            isOled = true,
            dominantColorHex = 0xFF00F0FF
        ),

        // Nature Category
        Wallpaper(
            id = "wp_nature_1",
            title = "Nordic Aurora Pulse",
            category = WallpaperCategory.NATURE,
            remoteUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "Solar Wind",
            tags = listOf("Aurora", "Borealis", "Green Glow", "Night", "Stars"),
            downloads = 19400,
            likes = 4300,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFF00F5A0
        ),
        Wallpaper(
            id = "wp_nature_2",
            title = "Obsidian Peaks",
            category = WallpaperCategory.NATURE,
            remoteUrl = "https://images.unsplash.com/photo-1519681393784-d120267933ba?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "Summit Lens",
            tags = listOf("Mountains", "Galaxy", "Dark Night", "OLED", "Stars"),
            downloads = 23100,
            likes = 5900,
            isFeatured = true,
            isOled = true,
            dominantColorHex = 0xFF7928CA
        ),
        Wallpaper(
            id = "wp_nature_3",
            title = "Misty Glacial Fjord",
            category = WallpaperCategory.NATURE,
            remoteUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1080&q=85",
            resolution = "Quad HD (2560×1440)",
            author = "Wanderer",
            tags = listOf("Fjord", "Mist", "Reflection", "Serene", "Landscape"),
            downloads = 14200,
            likes = 3100,
            isFeatured = false,
            isOled = false,
            dominantColorHex = 0xFF00B4D8
        ),

        // Neon Category
        Wallpaper(
            id = "wp_neon_1",
            title = "Arcade Syndicate",
            category = WallpaperCategory.NEON,
            remoteUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "RetroWave 84",
            tags = listOf("Arcade", "Retro", "Neon", "80s Gaming", "Vibrant"),
            downloads = 37800,
            likes = 9500,
            isFeatured = true,
            isOled = true,
            dominantColorHex = 0xFFFF007A
        ),
        Wallpaper(
            id = "wp_neon_2",
            title = "Ultraviolet Pulse",
            category = WallpaperCategory.NEON,
            remoteUrl = "https://images.unsplash.com/photo-1563089145-599997674d42?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "Lumen FX",
            tags = listOf("Neon Tubes", "Purple", "Cyan", "Glowing", "Minimal"),
            downloads = 25400,
            likes = 6200,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFF9D4EDD
        ),
        Wallpaper(
            id = "wp_neon_3",
            title = "Cyber Gridway",
            category = WallpaperCategory.NEON,
            remoteUrl = "https://images.unsplash.com/photo-1550684848-fac1c5b4e853?auto=format&fit=crop&w=1080&q=85",
            resolution = "Quad HD (2560×1440)",
            author = "Matrix Shift",
            tags = listOf("Grid", "Perspective", "Synthwave", "Cyber", "Dark"),
            downloads = 21900,
            likes = 5400,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFF00F0FF
        ),

        // Additional Battle Royale
        Wallpaper(
            id = "wp_battle_2",
            title = "Neon Arena Glitch",
            category = WallpaperCategory.BATTLE_ROYALE,
            remoteUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "FragZone",
            tags = listOf("Esports", "Arena", "Gaming Rig", "RGB", "Tournament"),
            downloads = 29600,
            likes = 8100,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFFFF5722
        ),
        Wallpaper(
            id = "wp_battle_3",
            title = "Shadow Ops Rig",
            category = WallpaperCategory.BATTLE_ROYALE,
            remoteUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?auto=format&fit=crop&w=1080&q=85",
            resolution = "Quad HD (2560×1440)",
            author = "Stealth Strike",
            tags = listOf("Controller", "Dark", "Tactical", "Pro Gaming", "OLED"),
            downloads = 22100,
            likes = 4900,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFF0091FF
        ),

        // Additional Cars
        Wallpaper(
            id = "wp_cars_2",
            title = "Midnight Phantom",
            category = WallpaperCategory.CARS,
            remoteUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "Speed Hunter",
            tags = listOf("Sports Car", "Headlights", "Dark Matte", "Exotic", "Supercar"),
            downloads = 33700,
            likes = 8800,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFFFFB703
        ),
        Wallpaper(
            id = "wp_cars_3",
            title = "Cyber Stingray",
            category = WallpaperCategory.CARS,
            remoteUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "Apex Motor",
            tags = listOf("Corvette", "Tail Lights", "Asphalt", "Dark Blue", "Beast"),
            downloads = 27300,
            likes = 6900,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFF0051FF
        ),

        // Additional Anime & Abstract
        Wallpaper(
            id = "wp_anime_2",
            title = "Cosmic Sorceress",
            category = WallpaperCategory.ANIME,
            remoteUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "Manga Dreams",
            tags = listOf("Fantasy", "Cosmic", "Magic", "Anime Art", "Vivid"),
            downloads = 31400,
            likes = 8200,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFFBD00FF
        ),
        Wallpaper(
            id = "wp_abstract_2",
            title = "Chromatic Flux",
            category = WallpaperCategory.ABSTRACT,
            remoteUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "Vector Void",
            tags = listOf("Fluid", "Holographic", "Waves", "Modern Art", "Smooth"),
            downloads = 24800,
            likes = 6300,
            isFeatured = false,
            isOled = false,
            dominantColorHex = 0xFFFF0055
        ),
        Wallpaper(
            id = "wp_abstract_3",
            title = "Prismatic Core",
            category = WallpaperCategory.ABSTRACT,
            remoteUrl = "https://images.unsplash.com/photo-1634017839464-5c339ebe3cb4?auto=format&fit=crop&w=1080&q=85",
            resolution = "4K UHD (3840×2160)",
            author = "Poly Render",
            tags = listOf("Low Poly", "Refraction", "Glass", "3D Shape", "Geometry"),
            downloads = 19800,
            likes = 5100,
            isFeatured = false,
            isOled = true,
            dominantColorHex = 0xFF7928CA
        )
    )

    fun getByCategory(category: WallpaperCategory): List<Wallpaper> {
        return sampleWallpapers.filter { it.category == category }
    }

    fun getFeatured(): List<Wallpaper> {
        return sampleWallpapers.filter { it.isFeatured }
    }

    fun search(query: String, category: WallpaperCategory? = null): List<Wallpaper> {
        val trimmed = query.trim().lowercase()
        return sampleWallpapers.filter { wp ->
            val matchesCategory = category == null || wp.category == category
            val matchesQuery = trimmed.isEmpty() ||
                wp.title.lowercase().contains(trimmed) ||
                wp.category.displayName.lowercase().contains(trimmed) ||
                wp.tags.any { it.lowercase().contains(trimmed) }
            matchesCategory && matchesQuery
        }
    }
}
