package com.example.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.WallpaperDataSource
import com.example.model.Wallpaper
import com.example.model.WallpaperCategory
import com.example.ui.components.WallpaperCard
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonMagenta
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    selectedCategoryFilter: WallpaperCategory?,
    onCategoryFilterChange: (WallpaperCategory?) -> Unit,
    favoriteIds: Set<String>,
    onWallpaperClick: (Wallpaper) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val wallpapers = if (selectedCategoryFilter == null) {
        WallpaperDataSource.sampleWallpapers
    } else {
        WallpaperDataSource.getByCategory(selectedCategoryFilter)
    }

    val featuredList = WallpaperDataSource.getFeatured()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 156.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
            .testTag("home_screen")
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // App Header
        item(span = { GridItemSpan(maxLineSpan) }) {
            HomeHeader(onSearchClick = onSearchClick)
        }

        // Featured Hero Section (when All is selected)
        if (selectedCategoryFilter == null && featuredList.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                FeaturedCarousel(
                    featuredWallpapers = featuredList,
                    onWallpaperClick = onWallpaperClick
                )
            }
        }

        // Category Filter Chips
        item(span = { GridItemSpan(maxLineSpan) }) {
            CategoryFilterBar(
                selectedCategory = selectedCategoryFilter,
                onCategorySelected = onCategoryFilterChange
            )
        }

        // Section Title & Counter
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Bolt,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (selectedCategoryFilter == null) "All Wallpapers" else "${selectedCategoryFilter.displayName} Pro",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Text(
                    text = "${wallpapers.size} available",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        // Wallpaper Grid Items
        items(
            items = wallpapers,
            key = { it.id }
        ) { wallpaper ->
            WallpaperCard(
                wallpaper = wallpaper,
                isFavorite = favoriteIds.contains(wallpaper.id),
                onCardClick = { onWallpaperClick(wallpaper) },
                onFavoriteToggle = { onFavoriteToggle(wallpaper.id) }
            )
        }
    }
}

@Composable
private fun HomeHeader(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "GAMING",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = NeonCyan,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "WALLPAPER",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            Brush.horizontalGradient(listOf(NeonMagenta, NeonPurple))
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "PRO",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }
            Text(
                text = "Premium 4K Ultra HD & OLED Collection",
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(DarkSurfaceElevated)
                .border(1.dp, DarkCardBorder, CircleShape)
        ) {
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("home_search_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Wallpapers",
                    tint = NeonCyan,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun FeaturedCarousel(
    featuredWallpapers: List<Wallpaper>,
    onWallpaperClick: (Wallpaper) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AutoAwesome,
                contentDescription = null,
                tint = NeonMagenta,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Featured Spotlight",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(featuredWallpapers, key = { "featured_${it.id}" }) { wp ->
                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 1.5.dp,
                            brush = Brush.horizontalGradient(listOf(NeonCyan, NeonPurple)),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { onWallpaperClick(wp) }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(wp.localDrawableId ?: wp.remoteUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = wp.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                                )
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(NeonCyan.copy(alpha = 0.2f))
                                    .border(1.dp, NeonCyan, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = wp.category.displayName.uppercase(),
                                    color = NeonCyan,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = wp.title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterBar(
    selectedCategory: WallpaperCategory?,
    onCategorySelected: (WallpaperCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp),
        modifier = modifier
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All", fontWeight = FontWeight.SemiBold) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = NeonCyan,
                    selectedLabelColor = Color(0xFF090B11),
                    containerColor = DarkSurfaceElevated,
                    labelColor = TextSecondary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selectedCategory == null,
                    borderColor = DarkCardBorder,
                    selectedBorderColor = NeonCyan
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.testTag("filter_chip_all")
            )
        }

        items(WallpaperCategory.entries.toTypedArray()) { category ->
            val isSelected = selectedCategory == category
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(if (isSelected) null else category) },
                label = { Text(category.displayName, fontWeight = FontWeight.SemiBold) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = NeonCyan,
                    selectedLabelColor = Color(0xFF090B11),
                    containerColor = DarkSurfaceElevated,
                    labelColor = TextSecondary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = DarkCardBorder,
                    selectedBorderColor = NeonCyan
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.testTag("filter_chip_${category.id}")
            )
        }
    }
}
