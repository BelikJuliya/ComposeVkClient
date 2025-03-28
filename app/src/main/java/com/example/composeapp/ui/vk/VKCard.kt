package com.example.composeapp.ui.vk

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.composeapp.ui.theme.ComposeAppTheme

@Composable
fun VkPost(
    userName: String,
    time: String,
    userImageRes: Int,
    content: String,
    contentImageRes: Int
) {
    Card(
        modifier = Modifier
            .padding(16.dp),
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
            TitleRow(
                time = time,
                userName = userName,
                userImageRes =
                    userImageRes
            )
            Text(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth(),
                text = content,
                color = MaterialTheme.colorScheme.onBackground
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 4.dp),
                painter = painterResource(contentImageRes),
                contentDescription = "Fish",
                contentScale = ContentScale.FillWidth
            )
            BottomRow()
        }
    }
}

@Preview
@Composable
private fun VkPostPreviewLight() {
    ComposeAppTheme(darkTheme = false) {
        VkPost(
            userName = "Уволено",
            time = "14:00",
            userImageRes = R.drawable.me,
            content = "Кабаныч, когда узнал, что если сотрудникам не платить, то они умирают от голода",
            contentImageRes = R.drawable.homeless
        )
    }
}

@Preview
@Composable
private fun VkPostPreviewDark() {
    ComposeAppTheme(darkTheme = true) {
        VkPost(
            userName = "Уволено",
            time = "14:00",
            userImageRes = R.drawable.me,
            content = "Кабаныч, когда узнал, что если сотрудникам не платить, то они умирают от голода",
            contentImageRes = R.drawable.homeless
        )
    }
}

@Composable
fun TitleRow(
    time: String,
    userName: String,
    userImageRes: Int
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
            painter = painterResource(userImageRes),
            contentDescription = "Avatar",
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(weight = 1f),
        ) {
            Text(
                text = userName,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = time,
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
        TitleRow(
            userName = "Уволено",
            time = "14:00",
            userImageRes = R.drawable.me,
        )
    }
}

@Preview
@Composable
fun PreviewTitleRoDark() {
    ComposeAppTheme(darkTheme = true) {
        TitleRow(
            userName = "Уволено",
            time = "14:00",
            userImageRes = R.drawable.me,
        )
    }
}

@Composable
fun BottomRow(
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .weight(weight = 1f)
        ) {
            IconText(text = "916", imageRes = R.drawable.ic_eye)
        }
        Row(
            modifier = Modifier
                .weight(weight = 1f)
        ) {
            IconText(text = "7", imageRes = R.drawable.ic_repost)
            Spacer(modifier = Modifier.size(16.dp))
            IconText(text = "8", imageRes = R.drawable.ic_comments)
            Spacer(modifier = Modifier.size(16.dp))
            IconText(text = "23", imageRes = R.drawable.ic_like)
        }
    }
}

@Composable
fun IconText(imageRes: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
        IconText(text = "916", imageRes = R.drawable.ic_eye)
    }
}

@Preview
@Composable
fun IconTextPreviewDark() {
    ComposeAppTheme(darkTheme = true) {
        IconText(text = "916", imageRes = R.drawable.ic_eye)
    }
}

@Preview
@Composable
fun PreviewBottomRowLight() {
    ComposeAppTheme(darkTheme = false) {
        BottomRow()
    }
}

@Preview
@Composable
fun PreviewBottomRowDark() {
    ComposeAppTheme(darkTheme = true) {
        BottomRow()
    }
}