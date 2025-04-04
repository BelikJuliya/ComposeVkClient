package com.example.composeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.navigation.Screen.Companion.KEY_FEED_POST_ID

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(name = KEY_FEED_POST_ID, builder = {
                    type = NavType.IntType
                })
            )
        ) {
            val feedPosId = it.arguments?.getInt(KEY_FEED_POST_ID) ?: 0
            commentsScreenContent(FeedPost(id = feedPosId))
        }
    }
}