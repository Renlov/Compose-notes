package com.p1.composenote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.p1.composenote.ui.navigation.Screen
import com.p1.composenote.ui.navigation.SetupNavGraph
import com.p1.composenote.ui.navigation.navigateToSingleTop
import com.p1.composenote.ui.presentation.viewmodel.BookmarkViewModel
import com.p1.composenote.ui.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            val navigationController = rememberNavController()
            val bookmarkViewModel = koinViewModel<BookmarkViewModel>()
            val homeScreenViewModel = koinViewModel<HomeScreenViewModel>()
            var currentTab by remember {
                mutableStateOf(TabScreen.Home)
            }
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                InputChip(
                                    selected = currentTab == TabScreen.Home,
                                    onClick = {
                                        currentTab = TabScreen.Home
                                        navigationController.navigateToSingleTop(
                                            router = Screen.Home.route
                                        )
                                    },
                                    label = {
                                        Text("Home")
                                    },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = null
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.Companion.size(12.dp))
                                InputChip(
                                    selected = currentTab == TabScreen.Bookmark,
                                    onClick = {
                                        currentTab = TabScreen.Bookmark
                                        navigationController.navigateToSingleTop(
                                            router = Screen.Bookmark.route
                                        )
                                    },
                                    label = {
                                        Text("Bookmark")
                                    },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.FavoriteBorder,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    navigationController.navigateToSingleTop(
                                        router = Screen.Detail.route
                                    )
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                            }
                        }
                    )
                }
            ) {
                SetupNavGraph(
                    modifier = Modifier.padding(it),
                    navController = navigationController,
                    bookmarkViewModel = bookmarkViewModel,
                    homeScreenViewModel = homeScreenViewModel
                )
            }

        }
    }
}

enum class TabScreen {
    Home, Bookmark
}