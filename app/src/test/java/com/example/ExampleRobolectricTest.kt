package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.FavoritesRepository
import com.example.data.WallpaperDataSource
import com.example.model.WallpaperCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Gaming Wallpaper Pro", appName)
    }

    @Test
    fun `verify categories coverage in data source`() {
        for (category in WallpaperCategory.entries) {
            val wallpapers = WallpaperDataSource.getByCategory(category)
            assertTrue("Category $category should have wallpapers", wallpapers.isNotEmpty())
        }
    }

    @Test
    fun `verify favorites repository toggle`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = FavoritesRepository.getInstance(context)

        val testId = "wp_battle_1"
        repo.removeFavorite(testId)
        assertFalse(repo.isFavorite(testId))

        val isFav = repo.toggleFavorite(testId)
        assertTrue(isFav)
        assertTrue(repo.isFavorite(testId))

        val isFavAfterSecondToggle = repo.toggleFavorite(testId)
        assertFalse(isFavAfterSecondToggle)
        assertFalse(repo.isFavorite(testId))
    }
}
