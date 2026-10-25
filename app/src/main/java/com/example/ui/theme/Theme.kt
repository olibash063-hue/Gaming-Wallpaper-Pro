package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val GamingDarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = DarkSurfaceElevated,
    onPrimaryContainer = NeonCyan,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = DarkSurfaceElevated,
    onSecondaryContainer = NeonPurple,
    tertiary = TertiaryDark,
    onTertiary = DarkBackground,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = DarkCardBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force premium dark gaming theme
    dynamicColor: Boolean = false, // Keep intentional gaming brand colors
    content: @Composable () -> Unit
) {
    val colorScheme = GamingDarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                it.statusBarColor = BackgroundDark.toArgb()
                it.navigationBarColor = DarkBackground.toArgb()
                WindowCompat.getInsetsController(it, view).apply {
                    isAppearanceLightStatusBars = false
                    isAppearanceLightNavigationBars = false
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
