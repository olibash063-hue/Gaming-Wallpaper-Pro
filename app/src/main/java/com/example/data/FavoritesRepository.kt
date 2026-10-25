package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("gaming_wallpaper_favorites", Context.MODE_PRIVATE)

    private val _favoriteIds = MutableStateFlow<Set<String>>(loadFavorites())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    private fun loadFavorites(): Set<String> {
        val raw = prefs.getStringSet(KEY_FAVORITES, null) ?: emptySet()
        return raw.toSet()
    }

    fun isFavorite(wallpaperId: String): Boolean {
        return _favoriteIds.value.contains(wallpaperId)
    }

    fun toggleFavorite(wallpaperId: String): Boolean {
        val current = _favoriteIds.value.toMutableSet()
        val isNowFav = if (current.contains(wallpaperId)) {
            current.remove(wallpaperId)
            false
        } else {
            current.add(wallpaperId)
            true
        }
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
        _favoriteIds.value = current
        return isNowFav
    }

    fun addFavorite(wallpaperId: String) {
        val current = _favoriteIds.value.toMutableSet()
        if (current.add(wallpaperId)) {
            prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
            _favoriteIds.value = current
        }
    }

    fun removeFavorite(wallpaperId: String) {
        val current = _favoriteIds.value.toMutableSet()
        if (current.remove(wallpaperId)) {
            prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
            _favoriteIds.value = current
        }
    }

    companion object {
        private const val KEY_FAVORITES = "fav_ids"

        @Volatile
        private var instance: FavoritesRepository? = null

        fun getInstance(context: Context): FavoritesRepository {
            return instance ?: synchronized(this) {
                instance ?: FavoritesRepository(context).also { instance = it }
            }
        }
    }
}
