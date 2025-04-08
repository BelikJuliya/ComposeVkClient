package com.example.composeapp.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun UserPreview() {
    UserInfo(name = "Oleg", age = 28)
}

@Composable
fun UserInfo(name: String, age: Int) {
    Column {
        repeat(10) {
            Text(text = "Hello, $name! You are $age years old")
        }
    }
}

@Preview
@Composable
fun TimesTable() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        for (i in 1 until 10) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                for (j in 1 until 10) {
                    val boxBackgroundColor = if ((i + j) % 2 == 0) {
                        Color.Yellow
                    } else Color.White
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(weight = 1f)
                            .background(color = boxBackgroundColor)
                            .border(width = 1.dp, color = Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${i * j}")
                    }
                }
            }
        }
    }
}