package com.example.composeapp.ui.vk.comments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.domain.PostComment

@Composable
fun CommentsScreen(viewModel: CommentsViewModel, paddingValues: PaddingValues) {
    val listState = rememberLazyListState()
    val postsList = viewModel.items.observeAsState(emptyList())

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
                    CommentItem(
                        postComment = model
                    )
                }
            }
        }
    }
}

@Composable
fun CommentItem(postComment: PostComment) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .padding(start = 8.dp)
                .clip(CircleShape)
                .size(60.dp),
            painter = painterResource(postComment.authorAvatarId),
            contentDescription = "Author",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = postComment.authorName,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = postComment.commentText,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = postComment.publicationDate,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun commentItemPreview() {
    CommentItem(PostComment())
}