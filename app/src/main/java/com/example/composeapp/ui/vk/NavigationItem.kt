package com.example.composeapp.ui.vk

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import com.example.composeapp.R
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val titleResId: Int,
    val icon: ImageVector
) {

    data object Home : NavigationItem(
        titleResId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    data object Profile : NavigationItem(
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )

    data object Favourite : NavigationItem(
        titleResId = R.string.navigation_item_favourite,
        icon = Icons.Outlined.Favorite
    )
}