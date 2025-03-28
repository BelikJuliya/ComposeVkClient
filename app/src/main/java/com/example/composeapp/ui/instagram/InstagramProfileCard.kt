package com.example.composeapp.ui.instagram

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.R
import com.example.composeapp.ui.theme.ComposeAppTheme

@Composable
fun InstagramCard(
    viewModel: InstagramViewModel
) {
    val isFollowed: State<Boolean> = viewModel.isFollowing.observeAsState(false)

    var a: Int by remember {
        mutableIntStateOf(5)
    }

    val b: Int = a
    a = 10

    Card(
        modifier = Modifier
            .padding(all = 8.dp),
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(size = 60.dp)
                        .clip(CircleShape),
                    contentDescription = "Instagram",
                    painter = painterResource(R.drawable.ic_instagram)

                )
                UserStatistics(title = "Posts", value = "6,950")
                UserStatistics(title = "Followers", value = "436M")
                UserStatistics(title = "Following", value = "76")
            }

            Text(
                text = "Instagram",
                fontFamily = FontFamily.Cursive,
                fontSize = 32.sp
            )
            Text(
                text = "#YoursToMake",
                fontFamily = FontFamily.Serif,
                fontSize = 14.sp
            )
            Text(
                text = "www.instagram.com/toxi_1722/p/Cie2pmMstWP/",
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                fontSize = 14.sp
            )
            FollowButton(
                isFollowed = isFollowed,
                onClickListener = {
                    viewModel.changeFollowingStatus()
                }
            )
        }
    }
}

@Composable
fun FollowButton(
    isFollowed: State<Boolean>,
    onClickListener: () -> Unit
) {
    Button(
        onClick = {
            onClickListener()
        },
        enabled = true,
        shape = RoundedCornerShape(corner = CornerSize(size = 4.dp)),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFollowed.value) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else MaterialTheme.colorScheme.primary
        )
    ) {
        val text = if (isFollowed.value) "Unfollow" else "Follow"
        Text(text = text)
    }
}

@Composable
private fun UserStatistics(title: String, value: String) {
    Column(
        modifier = Modifier
            .height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = title,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive

        )
    }
}

//@Preview
//@Composable
//fun previewCardLight() {
//    ComposeAppTheme(darkTheme = false) {
//        InstagramCard()
//    }
//}
//
//@Preview
//@Composable
//fun previewCardDark() {
//    ComposeAppTheme(darkTheme = true) {
//        InstagramCard()
//    }
//}