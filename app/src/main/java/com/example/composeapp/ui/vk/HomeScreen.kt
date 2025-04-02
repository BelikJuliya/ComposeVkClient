package com.example.composeapp.ui.vk

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.ui.vk.news.PostCard

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val listState = rememberLazyListState()
    val postsList = viewModel.feedPost.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 72.dp,
            bottom = 72.dp,
            start = 4.dp,
            end = 4.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        items(
            items = postsList.value,
            key = { it.id }
        ) { model ->
            // Состояние видимости
            var isVisible by remember { mutableStateOf(true) }

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        isVisible = false // Скрываем элемент
                        true
                    } else {
                        false
                    }
                }
            )

            // Управляем анимацией через MutableTransitionState
            val transitionState = remember { MutableTransitionState(true) }
            transitionState.targetState = isVisible

            AnimatedVisibility(
                visibleState = transitionState,
                enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                exit = fadeOut() + slideOutHorizontally(targetOffsetX = { -it })
            ) {
                // Ждём завершения анимации
                LaunchedEffect(transitionState.isIdle && !transitionState.currentState) {
                    // Если элемент исчез
                    if (transitionState.isIdle && !transitionState.currentState) {
                        viewModel.deleteItem(model)
                    }
                }

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .background(Color.Red.copy(alpha = 0.5f))
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Delete item",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 24.sp
                            )
                        }
                    }
                ) {
                    PostCard(
                        feedPost = model,
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
    }
}