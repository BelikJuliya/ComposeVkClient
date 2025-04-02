package com.example.composeapp.ui.vk.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.PostComment

@Composable
fun CommentsScreen(
    feedPost: FeedPost,
    comments: List<PostComment>,
    onBackPressed: () -> Unit
) {

    Scaffold(
        topBar = {
            CommentsAppBar(feedPost = feedPost, onBackPressed = onBackPressed)
        },
        content = {
            CommentsList(
                paddingValues = it,
                comments = comments
            )
        }
    )
}

@Composable
fun CommentItem(postComment: PostComment) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp),
            painter = painterResource(postComment.authorAvatarId),
            contentDescription = "Author",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = postComment.authorName,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = postComment.commentText,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = postComment.publicationDate,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CommentsList(
    paddingValues: PaddingValues,
    comments: List<PostComment>
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState
    ) {
        items(
            items = comments,
            key = { it.id }
        ) { model ->
            CommentItem(postComment = model)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsAppBar(feedPost: FeedPost, onBackPressed: () -> Unit = {}) {
    TopAppBar(
        title = {
            Text(text = "Comments for post id ${feedPost.id}")
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressed()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
fun AppBarPreview() {
    CommentsAppBar(FeedPost())
}

@Preview
@Composable
fun commentItemPreview() {
    CommentItem(PostComment())
}