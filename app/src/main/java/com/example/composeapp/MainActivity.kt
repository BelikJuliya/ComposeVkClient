package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.composeapp.ui.instagram.InstagramCard
import com.example.composeapp.ui.instagram.InstagramViewModel
import com.example.composeapp.ui.vk.MainScreen
import com.example.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {

//    val viewModel = ViewModelProvider(this)[InstagramViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                ) {
//                    InstagramCard()
                    MainScreen()
                }
            }
        }
    }


}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent { TestScreen() }
//    }
//}
//
//@Composable
//fun TestScreen() {
//    val (clicked, setClicked) = remember { mutableStateOf(false) }
//    Button(onClick = {
//        setClicked(!clicked) }
//    ) {
//        Text(if (clicked) "Clicked!" else "Click me")
//    }
//}