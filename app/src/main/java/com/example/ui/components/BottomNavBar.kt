package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.AppTab

@Composable
fun BottomNavBar(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit,
    favoritesCount: Int,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .border(
                width = 1.dp,
                color = DarkCardBorder,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ),
        containerColor = DarkSurface,
        windowInsets = WindowInsets.navigationBars
    ) {
        val items = listOf(
            Triple(AppTab.HOME, Icons.Filled.Home, Icons.Outlined.Home),
            Triple(AppTab.CATEGORIES, Icons.Filled.GridView, Icons.Outlined.GridView),
            Triple(AppTab.FAVORITES, Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
            Triple(AppTab.SEARCH, Icons.Filled.Search, Icons.Outlined.Search)
        )

        items.forEach { (tab, filledIcon, outlinedIcon) ->
            val isSelected = currentTab == tab

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) filledIcon else outlinedIcon,
                        contentDescription = tab.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = if (tab == AppTab.FAVORITES && favoritesCount > 0) {
                            "${tab.title} ($favoritesCount)"
                        } else {
                            tab.title
                        },
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF090B11),
                    selectedTextColor = NeonCyan,
                    indicatorColor = NeonCyan,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted
                ),
                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
            )
        }
    }
}
