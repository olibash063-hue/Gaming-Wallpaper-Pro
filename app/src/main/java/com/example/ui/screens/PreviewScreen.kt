package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.model.Wallpaper
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceGlass
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonMagenta
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.WallpaperTarget
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    wallpaper: Wallpaper,
    isFavorite: Boolean,
    isActionInProgress: Boolean,
    isLockScreenMockup: Boolean,
    onBack: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onToggleLockScreenMockup: () -> Unit,
    onSetWallpaper: (WallpaperTarget) -> Unit,
    onDownload: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var isUiVisible by remember { mutableStateOf(true) }
    var showSetWallpaperSheet by remember { mutableStateOf(false) }
    var showInfoSheet by remember { mutableStateOf(false) }

    val setWallpaperSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val infoSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .testTag("preview_screen")
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isUiVisible = !isUiVisible
            }
    ) {
        // Fullscreen Wallpaper Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(wallpaper.localDrawableId ?: wallpaper.remoteUrl)
                .crossfade(true)
                .build(),
            contentDescription = wallpaper.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Lock Screen Clock & Notification Mockup Overlay
        AnimatedVisibility(
            visible = isLockScreenMockup,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            LockScreenMockupOverlay()
        }

        // Top App Bar Controls
        AnimatedVisibility(
            visible = isUiVisible,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Black.copy(alpha = 0.85f), Color.Transparent)
                        )
                    )
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back Button
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .testTag("preview_back_button")
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceGlass)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    // Title
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = wallpaper.title,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = wallpaper.category.displayName,
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Top Action Icons
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Mockup Mode Toggle Button
                        IconButton(
                            onClick = onToggleLockScreenMockup,
                            modifier = Modifier
                                .testTag("preview_mockup_toggle")
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(if (isLockScreenMockup) NeonCyan else DarkSurfaceGlass)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PhoneAndroid,
                                contentDescription = "Lock Screen Mockup",
                                tint = if (isLockScreenMockup) Color.Black else Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Favorite Button
                        IconButton(
                            onClick = onFavoriteToggle,
                            modifier = Modifier
                                .testTag("preview_favorite_button")
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceGlass)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) NeonMagenta else Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // Bottom Action Bar Controls
        AnimatedVisibility(
            visible = isUiVisible,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.95f))
                        )
                    )
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Quick Info Tag Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkSurfaceGlass)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = wallpaper.resolution,
                                    color = NeonCyan,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkSurfaceGlass)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "By ${wallpaper.author}",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Info Icon
                        IconButton(
                            onClick = { showInfoSheet = true },
                            modifier = Modifier
                                .testTag("preview_info_button")
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Wallpaper Info",
                                tint = TextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Main Actions: Download and Set Wallpaper
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Download to Device Button
                        Button(
                            onClick = onDownload,
                            enabled = !isActionInProgress,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkSurfaceElevated,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .testTag("preview_download_button")
                                .weight(1f)
                                .height(54.dp)
                                .border(1.dp, DarkCardBorder, RoundedCornerShape(16.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Download,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Save",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        // Set Wallpaper Button
                        Button(
                            onClick = { showSetWallpaperSheet = true },
                            enabled = !isActionInProgress,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = Color(0xFF090B11)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .testTag("preview_set_wallpaper_button")
                                .weight(1.8f)
                                .height(54.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Wallpaper,
                                contentDescription = null,
                                tint = Color(0xFF090B11),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Set Wallpaper",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // Action Progress Indicator
        if (isActionInProgress) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = NeonCyan,
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Processing...",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Set Wallpaper Bottom Sheet
        if (showSetWallpaperSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSetWallpaperSheet = false },
                sheetState = setWallpaperSheetState,
                containerColor = DarkSurface,
                dragHandle = null
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Apply Wallpaper",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Choose where to set \"${wallpaper.title}\"",
                        fontSize = 13.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    // Target 1: Home Screen
                    SetWallpaperOption(
                        title = "Home Screen",
                        subtitle = "Apply to your main launcher screen",
                        icon = Icons.Filled.Wallpaper,
                        onClick = {
                            scope.launch { setWallpaperSheetState.hide() }.invokeOnCompletion {
                                showSetWallpaperSheet = false
                                onSetWallpaper(WallpaperTarget.HOME)
                            }
                        },
                        modifier = Modifier.testTag("set_home_screen_option")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Target 2: Lock Screen
                    SetWallpaperOption(
                        title = "Lock Screen",
                        subtitle = "Apply to your device lock screen",
                        icon = Icons.Filled.Lock,
                        onClick = {
                            scope.launch { setWallpaperSheetState.hide() }.invokeOnCompletion {
                                showSetWallpaperSheet = false
                                onSetWallpaper(WallpaperTarget.LOCK)
                            }
                        },
                        modifier = Modifier.testTag("set_lock_screen_option")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Target 3: Both Screens
                    SetWallpaperOption(
                        title = "Both Screens",
                        subtitle = "Apply to Home and Lock screen simultaneously",
                        icon = Icons.Filled.Visibility,
                        onClick = {
                            scope.launch { setWallpaperSheetState.hide() }.invokeOnCompletion {
                                showSetWallpaperSheet = false
                                onSetWallpaper(WallpaperTarget.BOTH)
                            }
                        },
                        modifier = Modifier.testTag("set_both_screens_option")
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Wallpaper Details Bottom Sheet
        if (showInfoSheet) {
            ModalBottomSheet(
                onDismissRequest = { showInfoSheet = false },
                sheetState = infoSheetState,
                containerColor = DarkSurface,
                dragHandle = null
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Wallpaper Specs",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    InfoRow("Title", wallpaper.title)
                    InfoRow("Category", wallpaper.category.displayName)
                    InfoRow("Resolution", wallpaper.resolution)
                    InfoRow("OLED Optimized", if (wallpaper.isOled) "Yes (Deep Black #000)" else "Standard")
                    InfoRow("Author", wallpaper.author)
                    InfoRow("Tags", wallpaper.tags.joinToString(", "))
                    InfoRow("License", "Royalty Free for Personal Use")

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
private fun SetWallpaperOption(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DarkSurfaceElevated)
            .border(1.dp, DarkCardBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(NeonCyan.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NeonCyan,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondary, fontSize = 13.sp)
        Text(text = value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun LockScreenMockupOverlay() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "10:24",
            color = Color.White,
            fontSize = 72.sp,
            fontWeight = FontWeight.ExtraLight,
            letterSpacing = (-2).sp
        )
        Text(
            text = "Saturday, October 24",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
