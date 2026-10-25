package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.WallpaperDataSource
import com.example.model.Wallpaper
import com.example.model.WallpaperCategory
import com.example.ui.components.WallpaperCard
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CategoriesScreen(
    selectedCategoryDetail: WallpaperCategory?,
    onCategoryClick: (WallpaperCategory) -> Unit,
    onBackFromDetail: () -> Unit,
    favoriteIds: Set<String>,
    onWallpaperClick: (Wallpaper) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (selectedCategoryDetail != null) {
        BackHandler { onBackFromDetail() }
        CategoryDetailView(
            category = selectedCategoryDetail,
            onBack = onBackFromDetail,
            favoriteIds = favoriteIds,
            onWallpaperClick = onWallpaperClick,
            onFavoriteToggle = onFavoriteToggle,
            modifier = modifier
        )
    } else {
        CategoryListView(
            onCategoryClick = onCategoryClick,
            modifier = modifier
        )
    }
}

@Composable
private fun CategoryListView(
    onCategoryClick: (WallpaperCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
            .testTag("categories_screen")
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = "CATEGORIES",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = NeonCyan,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Explore hand-crafted gaming aesthetics",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }
        }

        items(WallpaperCategory.entries.toTypedArray(), key = { it.id }) { category ->
            val count = WallpaperDataSource.getByCategory(category).size
            CategoryCard(
                category = category,
                wallpaperCount = count,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

@Composable
private fun CategoryCard(
    category: WallpaperCategory,
    wallpaperCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = getCategoryIcon(category)
    val gradientBrush = Brush.linearGradient(
        colors = category.gradientColors.map { Color(it) }
    )

    Card(
        modifier = modifier
            .testTag("category_card_${category.id}")
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, DarkCardBorder, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            // Background subtle gradient glow in corner
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(category.gradientColors.first()).copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Category Icon with gradient background
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(gradientBrush),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = category.displayName,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Category Title & Count
            Column(
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    text = category.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$wallpaperCount Wallpapers",
                    fontSize = 11.sp,
                    color = NeonCyan,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun CategoryDetailView(
    category: WallpaperCategory,
    onBack: () -> Unit,
    favoriteIds: Set<String>,
    onWallpaperClick: (Wallpaper) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val wallpapers = WallpaperDataSource.getByCategory(category)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 156.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
            .testTag("category_detail_${category.id}")
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .testTag("category_detail_back")
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(DarkSurfaceElevated)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to categories",
                        tint = NeonCyan
                    )
                }

                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(
                        text = category.displayName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = category.description,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        items(wallpapers, key = { it.id }) { wallpaper ->
            WallpaperCard(
                wallpaper = wallpaper,
                isFavorite = favoriteIds.contains(wallpaper.id),
                onCardClick = { onWallpaperClick(wallpaper) },
                onFavoriteToggle = { onFavoriteToggle(wallpaper.id) }
            )
        }
    }
}

private fun getCategoryIcon(category: WallpaperCategory): ImageVector {
    return when (category) {
        WallpaperCategory.BATTLE_ROYALE -> Icons.Filled.SportsEsports
        WallpaperCategory.CARS -> Icons.Filled.DirectionsCar
        WallpaperCategory.ANIME -> Icons.Filled.AutoAwesome
        WallpaperCategory.NATURE -> Icons.Filled.Landscape
        WallpaperCategory.NEON -> Icons.Filled.FlashOn
        WallpaperCategory.ABSTRACT -> Icons.Filled.Category
    }
}
