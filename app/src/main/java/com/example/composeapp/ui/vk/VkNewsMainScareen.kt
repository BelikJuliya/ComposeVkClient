package com.example.composeapp.ui.vk

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeapp.domain.FeedPost
import java.util.Collections.replaceAll

val TAG = "MainScreen"

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val feedPost = viewModel.feedPost.observeAsState(FeedPost())

    // Состояние выбранного элемента навигации
    val selectedItemPosition = remember { mutableIntStateOf(0) }
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
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.intValue == index,
                        onClick = {
                            selectedItemPosition.intValue = index
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
            PostCard(
                modifier = Modifier.padding(padding),
                feedPost = feedPost.value,
                onLikeClickListener = viewModel::updateCount,
                onShareClickListener = viewModel::updateCount,
                onCommentClickListener = viewModel::updateCount,
                onViewClickListener = viewModel::updateCount
            )
        }
    )
}
