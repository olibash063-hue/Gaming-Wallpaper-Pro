package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun SearchScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    selectedCategory: WallpaperCategory?,
    onCategoryChange: (WallpaperCategory?) -> Unit,
    favoriteIds: Set<String>,
    onWallpaperClick: (Wallpaper) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val results = WallpaperDataSource.search(query, selectedCategory)
    val popularTags = listOf("Cyberpunk", "Supercar", "OLED", "4K", "Warrior", "Aurora", "Synthwave", "Mecha")

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 156.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
            .testTag("search_screen")
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Search Header & Input Box
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Text(
                    text = "SEARCH",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = NeonCyan,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Find high-resolution wallpapers by title, tag, or category",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_input"),
                    placeholder = {
                        Text(
                            text = "Search 'Cyberpunk', 'Cars', '4K'...",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = NeonCyan
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = { onQueryChange("") },
                                modifier = Modifier.testTag("search_clear_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear search",
                                    tint = TextSecondary
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceElevated,
                        unfocusedContainerColor = DarkSurfaceElevated,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = DarkCardBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }
        }

        // Popular Tags
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = "Trending Tags",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(popularTags) { tag ->
                        val isSelected = query.equals(tag, ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) NeonCyan.copy(alpha = 0.2f) else DarkSurfaceElevated)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) NeonCyan else DarkCardBorder,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "#$tag",
                                color = if (isSelected) NeonCyan else TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.testTag("tag_$tag")
                            )
                        }
                    }
                }
            }
        }

        // Category Filter Chips
        item(span = { GridItemSpan(maxLineSpan) }) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { onCategoryChange(null) },
                        label = { Text("All Categories") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonPurple,
                            selectedLabelColor = Color.White,
                            containerColor = DarkSurfaceElevated,
                            labelColor = TextSecondary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }

                items(WallpaperCategory.entries.toTypedArray()) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { onCategoryChange(if (isSelected) null else cat) },
                        label = { Text(cat.displayName) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonPurple,
                            selectedLabelColor = Color.White,
                            containerColor = DarkSurfaceElevated,
                            labelColor = TextSecondary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }
        }

        // Result Count or Empty State
        if (results.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                EmptySearchView(
                    query = query,
                    onClearFilters = {
                        onQueryChange("")
                        onCategoryChange(null)
                    }
                )
            }
        } else {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "${results.size} Wallpapers Found",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }

            items(results, key = { it.id }) { wp ->
                WallpaperCard(
                    wallpaper = wp,
                    isFavorite = favoriteIds.contains(wp.id),
                    onCardClick = { onWallpaperClick(wp) },
                    onFavoriteToggle = { onFavoriteToggle(wp.id) }
                )
            }
        }
    }
}

@Composable
private fun EmptySearchView(
    query: String,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceElevated)
                    .border(1.dp, DarkCardBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.SearchOff,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No Wallpapers Found",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (query.isNotEmpty()) "No results matching \"$query\". Try a different term or category." else "No wallpapers match the active filters.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onClearFilters,
                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceElevated),
                border = ButtonDefaults.outlinedButtonBorder,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Clear Filters",
                    color = NeonCyan,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
