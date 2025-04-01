package com.example.composeapp.ui.vk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composeapp.domain.FeedPost

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
            val listState = rememberLazyListState()
            val postsList = viewModel.feedPost.observeAsState(emptyList())
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 72.dp,
                    bottom = 72.dp,
                    start = 4.dp,
                    end = 4.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                state = listState
            ) {
                items(items = postsList.value, key = { it.postId }
                ) {
                    PostCard(
                        modifier = Modifier.padding(padding),
                        feedPost = it,
                        onLikeClickListener = { statistics, feedPost ->
                            viewModel.updateCount(statistic = statistics, model = feedPost)
                        },
                        onShareClickListener = { statistics, feedPost ->
                            viewModel.updateCount(statistic = statistics, model = feedPost)
                        },
                        onCommentClickListener = { statistics, feedPost ->
                            viewModel.updateCount(statistic = statistics, model = feedPost)
                        },
                        onViewClickListener = { statistics, feedPost ->
                            viewModel.updateCount(statistic = statistics, model = feedPost)
                        }
                    )
                }
            }
        }
    )
}
