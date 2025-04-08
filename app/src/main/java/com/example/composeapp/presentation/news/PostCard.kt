package com.example.composeapp.presentation.news

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.R
import com.example.composeapp.domain.FeedPost
import com.example.composeapp.domain.StatisticItem
import com.example.composeapp.domain.StatisticType
import com.example.composeapp.ui.theme.ComposeAppTheme

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onViewClickListener: (StatisticItem, FeedPost) -> Unit = { _, _ -> },
    onLikeClickListener: (StatisticItem, FeedPost) -> Unit = { _, _ -> },
    onShareClickListener: (StatisticItem, FeedPost) -> Unit = { _, _ -> },
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit = { _, _ -> }
) {
    with(feedPost) {
        Card(
            shape = RoundedCornerShape(
                corner = CornerSize(size = 4.dp)
            ),
            border = BorderStroke(width = 1.dp, color = Color.Gray),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                PostHeader(
                    feedPost = feedPost
                )
                Text(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth(),
                    text = contentText,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(200.dp)
                        .padding(all = 4.dp),
                    painter = painterResource(contentImageResId),
                    contentDescription = "Fish",
                    contentScale = ContentScale.FillWidth
                )
                UserStatistics(
                    model = feedPost,
                    onCommentClickListener = { statistics, model ->
                        onCommentClickListener(statistics, model)
                    },
                    onLikeClickListener = { statistics, model ->
                        onLikeClickListener(statistics, model)
                    },
                    onViewClickListener = { statistics, model ->
                        onViewClickListener(statistics, model)
                    },
                    onShareClickListener = { statistics, model ->
                        onShareClickListener(statistics, model)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun VkPostPreviewLight() {
    ComposeAppTheme(darkTheme = false) {
        PostCard(
            feedPost = FeedPost(),
            onCommentClickListener = { _, _ -> },
            onLikeClickListener = { _, _ -> },
            onViewClickListener = { _, _ -> },
            onShareClickListener = { _, _ -> },
        )
    }
}

@Preview
@Composable
private fun VkPostPreviewDark() {
    ComposeAppTheme(darkTheme = true) {
        PostCard(
            feedPost = FeedPost(),
            onCommentClickListener = { _, _ -> },
            onLikeClickListener = { _, _ -> },
            onViewClickListener = { _, _ -> },
            onShareClickListener = { _, _ -> },
        )
    }
}

@Composable
fun PostHeader(
    feedPost: FeedPost
) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(feedPost.avatarResId),
            contentDescription = "Avatar",
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(weight = 1f),
        ) {
            Text(
                text = feedPost.communityName,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = feedPost.publicationDate,
                color = Color.Gray
            )
        }
        IconButton(
            onClick = { }
        ) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun PreviewTitleRowLight() {
    ComposeAppTheme(darkTheme = false) {
        PostHeader(
            FeedPost()
        )
    }
}

@Preview
@Composable
fun PreviewTitleRoDark() {
    ComposeAppTheme(darkTheme = true) {
        PostHeader(
            FeedPost()
        )
    }
}

@Composable
fun UserStatistics(
    model: FeedPost,
    onViewClickListener: (StatisticItem, FeedPost) -> Unit = { _, _ -> },
    onLikeClickListener: (StatisticItem, FeedPost) -> Unit = { _, _ -> },
    onShareClickListener: (StatisticItem, FeedPost) -> Unit = { _, _ -> },
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit = { _, _ -> }
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val viewItem = model.statistics.getItemByType(StatisticType.VIEWS)
        Row(
            modifier = Modifier
                .weight(weight = 1f)
        ) {
            IconText(
                text = viewItem.count.toString(),
                imageRes = R.drawable.ic_eye,
                onItemClickListener = {
                    onViewClickListener(viewItem, model)
                }
            )
        }
        Row(
            modifier = Modifier
                .weight(weight = 1f)
        ) {
            val sharesItem = model.statistics.getItemByType(StatisticType.SHARES)
            IconText(
                text = sharesItem.count.toString(),
                imageRes = R.drawable.ic_repost,
                onItemClickListener = {
                    onShareClickListener(sharesItem, model)
                }
            )
            Spacer(modifier = Modifier.size(16.dp))

            val commentItem = model.statistics.getItemByType(StatisticType.COMMENTS)
            IconText(
                text = commentItem.count.toString(),
                imageRes = R.drawable.ic_comments,
                onItemClickListener = {
                    onCommentClickListener(commentItem, model)
                }
            )
            Spacer(modifier = Modifier.size(16.dp))

            val likesItem = model.statistics.getItemByType(StatisticType.LIKES)
            IconText(
                text = likesItem.count.toString(),
                imageRes = R.drawable.ic_like,
                onItemClickListener = {
                    onLikeClickListener(likesItem, model)
                }
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

@Composable
fun IconText(
    imageRes: Int,
    text: String,
    onItemClickListener: () -> Unit
) {
    Row(
        Modifier.clickable { onItemClickListener() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(size = 24.dp),
            contentDescription = null,
            painter = painterResource(imageRes),
            tint = Color.Gray
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = text,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun IconTextPreviewLight() {
    ComposeAppTheme(darkTheme = false) {
        IconText(text = "916", imageRes = R.drawable.ic_eye, onItemClickListener = {})
    }
}

@Preview
@Composable
fun IconTextPreviewDark() {
    ComposeAppTheme(darkTheme = true) {
        IconText(text = "916", imageRes = R.drawable.ic_eye, onItemClickListener = {})
    }
}

@Preview
@Composable
fun PreviewBottomRowLight() {
    ComposeAppTheme(darkTheme = false) {
        UserStatistics(
            model = FeedPost()
        )
    }
}

@Preview
@Composable
fun PreviewBottomRowDark() {
    ComposeAppTheme(darkTheme = true) {
        UserStatistics(
            model = FeedPost()
        )
    }
}