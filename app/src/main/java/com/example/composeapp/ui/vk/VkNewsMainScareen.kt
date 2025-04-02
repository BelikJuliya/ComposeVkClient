package com.example.composeapp.ui.vk

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.navigation.AppNavGraph
import com.example.composeapp.navigation.rememberNavigationState
import com.example.composeapp.ui.vk.comments.CommentsScreen

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    val commentsToPost: MutableState<FeedPost?> = remember { mutableStateOf(null) }

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Favourite,
        NavigationItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(item.titleResId))
                        },
                    )
                }
            }
        },
        content = { padding ->
            AppNavGraph(
                navHostController = navigationState.navHostController,
                homeScreenContent = {
                    if (commentsToPost.value == null) {
                        HomeScreen(
                            paddingValues = padding,
                            onCommentClickListener = {
                                commentsToPost.value = it
                            }
                        )
                    } else {
                        CommentsScreen(
                            feedPost = commentsToPost.value!!,
                            onBackPressed = {
                                commentsToPost.value = null
                            }
                        )
                    }
                },
                favouriteScreenContent = {
                    Text("Favourite")
                },
                profileScreenContent = {
                    Text("Profile")
                }
            )
        }
    )
}
