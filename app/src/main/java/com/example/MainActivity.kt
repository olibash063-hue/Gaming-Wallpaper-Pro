package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PreviewScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.WallpaperViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                GamingWallpaperApp()
            }
        }
    }
}

@Composable
fun GamingWallpaperApp(
    viewModel: WallpaperViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val previewWallpaper by viewModel.previewWallpaper.collectAsStateWithLifecycle()
    val selectedCategoryDetail by viewModel.selectedCategoryForDetail.collectAsStateWithLifecycle()
    val homeCategoryFilter by viewModel.homeCategoryFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchCategory by viewModel.searchCategory.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    val isActionInProgress by viewModel.isActionInProgress.collectAsStateWithLifecycle()
    val isLockScreenMockup by viewModel.isLockScreenMockupActive.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.notifications.collectLatest { notification ->
            snackbarHostState.showSnackbar(message = notification.message)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        if (previewWallpaper != null) {
            PreviewScreen(
                wallpaper = previewWallpaper!!,
                isFavorite = favoriteIds.contains(previewWallpaper!!.id),
                isActionInProgress = isActionInProgress,
                isLockScreenMockup = isLockScreenMockup,
                onBack = { viewModel.closePreview() },
                onFavoriteToggle = { viewModel.toggleFavorite(previewWallpaper!!.id) },
                onToggleLockScreenMockup = { viewModel.toggleLockScreenMockup() },
                onSetWallpaper = { target ->
                    previewWallpaper?.let { wp ->
                        viewModel.setWallpaper(wp, target)
                    }
                },
                onDownload = {
                    previewWallpaper?.let { wp ->
                        viewModel.downloadWallpaper(wp)
                    }
                }
            )
        } else {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                containerColor = DarkBackground,
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                bottomBar = {
                    BottomNavBar(
                        currentTab = currentTab,
                        onTabSelected = { viewModel.selectTab(it) },
                        favoritesCount = favoriteIds.size
                    )
                }
            ) { innerPadding ->
                val contentModifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)

                when (currentTab) {
                    AppTab.HOME -> {
                        HomeScreen(
                            selectedCategoryFilter = homeCategoryFilter,
                            onCategoryFilterChange = { viewModel.setHomeCategoryFilter(it) },
                            favoriteIds = favoriteIds,
                            onWallpaperClick = { viewModel.openPreview(it) },
                            onFavoriteToggle = { viewModel.toggleFavorite(it) },
                            onSearchClick = { viewModel.selectTab(AppTab.SEARCH) },
                            modifier = contentModifier
                        )
                    }
                    AppTab.CATEGORIES -> {
                        CategoriesScreen(
                            selectedCategoryDetail = selectedCategoryDetail,
                            onCategoryClick = { viewModel.openCategoryDetail(it) },
                            onBackFromDetail = { viewModel.closeCategoryDetail() },
                            favoriteIds = favoriteIds,
                            onWallpaperClick = { viewModel.openPreview(it) },
                            onFavoriteToggle = { viewModel.toggleFavorite(it) },
                            modifier = contentModifier
                        )
                    }
                    AppTab.FAVORITES -> {
                        FavoritesScreen(
                            favoriteWallpapers = viewModel.getFavoriteWallpapers(),
                            onWallpaperClick = { viewModel.openPreview(it) },
                            onFavoriteToggle = { viewModel.toggleFavorite(it) },
                            onExploreClick = { viewModel.selectTab(AppTab.HOME) },
                            modifier = contentModifier
                        )
                    }
                    AppTab.SEARCH -> {
                        SearchScreen(
                            query = searchQuery,
                            onQueryChange = { viewModel.setSearchQuery(it) },
                            selectedCategory = searchCategory,
                            onCategoryChange = { viewModel.setSearchCategory(it) },
                            favoriteIds = favoriteIds,
                            onWallpaperClick = { viewModel.openPreview(it) },
                            onFavoriteToggle = { viewModel.toggleFavorite(it) },
                            modifier = contentModifier
                        )
                    }
                }
            }
        }

        // Global snackbar host if inside preview
        if (previewWallpaper != null) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .safeDrawingPadding()
                    .padding(16.dp)
            )
        }
    }
}
