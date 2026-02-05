package com.example.composeapp.presentation.main

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composeapp.navigation.AppNavGraph
import com.example.composeapp.navigation.rememberNavigationState
import com.example.composeapp.presentation.comments.CommentsScreen
import com.example.composeapp.presentation.news.NewsFeedScreen

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

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
                items.forEach { item ->
                    val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navigationState.navigateTo(item.screen.route)
                            }
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
                newsFeedScreenContent = {
                    NewsFeedScreen(
                        paddingValues = padding,
                        onCommentClickListener = {
                            navigationState.navigateToComments(it)
                        }
                    )
                },
                favouriteScreenContent = {
                    Text("Favourite")
                },
                profileScreenContent = {
                    Text("Profile")
                },
                commentsScreenContent = { feedPost ->
                    CommentsScreen(
                        feedPost = feedPost,
                        onBackPressed = {
                            navigationState.navHostController.popBackStack()
                        }
                    )
                },
            )
        }
    )
}
