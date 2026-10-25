package com.example.util

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.model.Wallpaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream

enum class WallpaperTarget(val label: String, val flags: Int) {
    HOME("Home Screen", if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) WallpaperManager.FLAG_SYSTEM else 0),
    LOCK("Lock Screen", if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) WallpaperManager.FLAG_LOCK else 0),
    BOTH("Both Screens", if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) (WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK) else 0)
}

object WallpaperHelper {

    suspend fun loadBitmap(context: Context, wallpaper: Wallpaper): Bitmap? = withContext(Dispatchers.IO) {
        try {
            if (wallpaper.localDrawableId != null) {
                BitmapFactory.decodeResource(context.resources, wallpaper.localDrawableId)
            } else if (!wallpaper.remoteUrl.isNullOrEmpty()) {
                val loader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(wallpaper.remoteUrl)
                    .allowHardware(false) // Hardware bitmaps cannot be copied or set directly to WallpaperManager
                    .build()
                val result = loader.execute(request)
                val drawable = result.drawable
                (drawable as? BitmapDrawable)?.bitmap ?: drawable?.toBitmap()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun setAsWallpaper(
        context: Context,
        wallpaper: Wallpaper,
        target: WallpaperTarget
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val bitmap = loadBitmap(context, wallpaper)
                ?: return@withContext Result.failure(Exception("Unable to decode wallpaper image."))

            val wallpaperManager = WallpaperManager.getInstance(context)
            if (!wallpaperManager.isWallpaperSupported) {
                return@withContext Result.failure(Exception("Wallpaper setting is not supported on this device."))
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, target.flags)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }
            Result.success("Applied to ${target.label} successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception(e.localizedMessage ?: "Failed to set wallpaper."))
        }
    }

    suspend fun saveToGallery(
        context: Context,
        wallpaper: Wallpaper
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val bitmap = loadBitmap(context, wallpaper)
                ?: return@withContext Result.failure(Exception("Failed to decode image data."))

            val safeTitle = wallpaper.title.replace("[^a-zA-Z0-9]".toRegex(), "_")
            val fileName = "GamingWP_${safeTitle}_${System.currentTimeMillis()}.jpg"

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/GamingWallpapers")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: return@withContext Result.failure(Exception("Could not create media storage entry."))

            var outputStream: OutputStream? = null
            try {
                outputStream = context.contentResolver.openOutputStream(uri)
                    ?: return@withContext Result.failure(Exception("Could not open media output stream."))

                val compressed = bitmap.compress(Bitmap.CompressFormat.JPEG, 98, outputStream)
                if (!compressed) {
                    return@withContext Result.failure(Exception("Failed to encode JPEG image."))
                }
            } finally {
                outputStream?.close()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(uri, contentValues, null, null)
            }

            Result.success("Saved to Pictures/GamingWallpapers")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception(e.localizedMessage ?: "Failed to save wallpaper."))
        }
    }
}
