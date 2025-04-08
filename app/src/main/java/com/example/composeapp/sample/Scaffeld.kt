package com.example.composeapp.sample

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Test() {
    ModalNavigationDrawer( //  <----  Оборачиваем Scaffold в ModalNavigationDrawer
        drawerContent = { // <----  Здесь определяем содержимое drawer
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Option 1") },
                    selected = true,
                    onClick = {
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text(text = "Option 2") },
                    selected = false,
                    onClick = {
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                // ... добавить больше пунктов меню
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "TopAppBar title")
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = true,
                            onClick = {

                            },
                            icon = {
                                Icon(Icons.Default.Favorite, contentDescription = null)
                            },
                            label = {
                                Text(text = "Favourite")
                            }
                        )
                        NavigationBarItem(
                            selected = true,
                            onClick = {

                            },
                            icon = {
                                Icon(Icons.Default.Delete, contentDescription = null)
                            },
                            label = {
                                Text(text = "Delete")
                            }
                        )
                        NavigationBarItem(
                            selected = true,
                            onClick = {

                            },
                            icon = {
                                Icon(Icons.Default.Edit, contentDescription = null)
                            },
                            label = {
                                Text(text = "Edit")
                            }
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                content = {
                    Text(
                        modifier = Modifier.padding(it),
                        text = "Hello World!"
                    )
                },
            )
        }
    )
}