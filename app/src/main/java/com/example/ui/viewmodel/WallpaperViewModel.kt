package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FavoritesRepository
import com.example.data.WallpaperDataSource
import com.example.model.Wallpaper
import com.example.model.WallpaperCategory
import com.example.util.WallpaperHelper
import com.example.util.WallpaperTarget
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppTab(val title: String) {
    HOME("Home"),
    CATEGORIES("Categories"),
    FAVORITES("Favorites"),
    SEARCH("Search")
}

data class UiNotification(
    val message: String,
    val isError: Boolean = false
)

class WallpaperViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository = FavoritesRepository.getInstance(application)

    // Navigation State
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    // Preview Screen State
    private val _previewWallpaper = MutableStateFlow<Wallpaper?>(null)
    val previewWallpaper: StateFlow<Wallpaper?> = _previewWallpaper.asStateFlow()

    // Category detail view (when tapping a category card in Categories tab)
    private val _selectedCategoryForDetail = MutableStateFlow<WallpaperCategory?>(null)
    val selectedCategoryForDetail: StateFlow<WallpaperCategory?> = _selectedCategoryForDetail.asStateFlow()

    // Home Filter
    private val _homeCategoryFilter = MutableStateFlow<WallpaperCategory?>(null)
    val homeCategoryFilter: StateFlow<WallpaperCategory?> = _homeCategoryFilter.asStateFlow()

    // Search State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchCategory = MutableStateFlow<WallpaperCategory?>(null)
    val searchCategory: StateFlow<WallpaperCategory?> = _searchCategory.asStateFlow()

    // Favorites State
    val favoriteIds: StateFlow<Set<String>> = favoritesRepository.favoriteIds

    // Action progress & messages
    private val _isActionInProgress = MutableStateFlow(false)
    val isActionInProgress: StateFlow<Boolean> = _isActionInProgress.asStateFlow()

    private val _notifications = MutableSharedFlow<UiNotification>()
    val notifications: SharedFlow<UiNotification> = _notifications.asSharedFlow()

    // Lock screen overlay preview mode in PreviewScreen
    private val _isLockScreenMockupActive = MutableStateFlow(false)
    val isLockScreenMockupActive: StateFlow<Boolean> = _isLockScreenMockupActive.asStateFlow()

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
        // If switching tabs, clear category detail so it returns to category list
        if (tab != AppTab.CATEGORIES) {
            _selectedCategoryForDetail.value = null
        }
    }

    fun openPreview(wallpaper: Wallpaper) {
        _previewWallpaper.value = wallpaper
    }

    fun closePreview() {
        _previewWallpaper.value = null
        _isLockScreenMockupActive.value = false
    }

    fun openCategoryDetail(category: WallpaperCategory) {
        _selectedCategoryForDetail.value = category
    }

    fun closeCategoryDetail() {
        _selectedCategoryForDetail.value = null
    }

    fun setHomeCategoryFilter(category: WallpaperCategory?) {
        _homeCategoryFilter.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSearchCategory(category: WallpaperCategory?) {
        _searchCategory.value = category
    }

    fun toggleLockScreenMockup() {
        _isLockScreenMockupActive.value = !_isLockScreenMockupActive.value
    }

    fun toggleFavorite(wallpaperId: String) {
        val isFav = favoritesRepository.toggleFavorite(wallpaperId)
        viewModelScope.launch {
            _notifications.emit(
                UiNotification(
                    message = if (isFav) "Added to favorites ❤️" else "Removed from favorites"
                )
            )
        }
    }

    fun setWallpaper(wallpaper: Wallpaper, target: WallpaperTarget) {
        viewModelScope.launch {
            _isActionInProgress.value = true
            val result = WallpaperHelper.setAsWallpaper(
                context = getApplication(),
                wallpaper = wallpaper,
                target = target
            )
            _isActionInProgress.value = false
            result.fold(
                onSuccess = { msg ->
                    _notifications.emit(UiNotification(message = msg, isError = false))
                },
                onFailure = { err ->
                    _notifications.emit(
                        UiNotification(
                            message = "Failed: ${err.message ?: "Unknown error"}",
                            isError = true
                        )
                    )
                }
            )
        }
    }

    fun downloadWallpaper(wallpaper: Wallpaper) {
        viewModelScope.launch {
            _isActionInProgress.value = true
            val result = WallpaperHelper.saveToGallery(
                context = getApplication(),
                wallpaper = wallpaper
            )
            _isActionInProgress.value = false
            result.fold(
                onSuccess = { msg ->
                    _notifications.emit(UiNotification(message = msg, isError = false))
                },
                onFailure = { err ->
                    _notifications.emit(
                        UiNotification(
                            message = "Download error: ${err.message ?: "Failed to save"}",
                            isError = true
                        )
                    )
                }
            )
        }
    }

    fun getFavoriteWallpapers(): List<Wallpaper> {
        val ids = favoriteIds.value
        return WallpaperDataSource.sampleWallpapers.filter { ids.contains(it.id) }
    }
}
