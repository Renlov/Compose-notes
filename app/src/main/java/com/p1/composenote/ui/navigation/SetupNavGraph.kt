package com.p1.composenote.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.p1.composenote.ui.presentation.screen.BookmarkScreen
import com.p1.composenote.ui.presentation.screen.DetailScreenMain
import com.p1.composenote.ui.presentation.screen.HomeScreen
import com.p1.composenote.ui.presentation.viewmodel.BookmarkViewModel
import com.p1.composenote.ui.presentation.viewmodel.HomeScreenViewModel

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
    homeScreenViewModel: HomeScreenViewModel,
    bookmarkViewModel: BookmarkViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Home.route) {
            val state by homeScreenViewModel.getHomeScreenState()
                .collectAsState(initial = HomeScreenViewModel.HomeScreenState.Loading)

            HomeScreen(
                state = state,
                onBookmarkChange = homeScreenViewModel::onBookMarkChange,
                onDeleteNote = homeScreenViewModel::deleteNote,
                onNoteClicked = { note ->
                    navController.navigateToSingleTop(
                        Screen.Detail.route + "?id=$note"
                    )
                }
            )
        }
        composable(route = Screen.Bookmark.route) {
            val state by bookmarkViewModel.getBookmarkScreenState()
                .collectAsState(initial = BookmarkViewModel.BookmarkScreenState.Loading)

            BookmarkScreen(
                modifier = modifier,
                state = state,
                onBookmarkChange = bookmarkViewModel::onBookmarkChange,
                onDeleteNote = bookmarkViewModel::deleteNotes,
                onNoteClicked = { note ->
                    navController.navigateToSingleTop(
                        Screen.Detail.route + "?id=$note"
                    )
                }
            )
        }
        composable(
            route = Screen.Detail.route + "?id={id}",
            arguments = listOf(navArgument("id") {
                NavType.LongType
                defaultValue = -1L
            }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong("id") ?: -1L
            DetailScreenMain(
                noteId = id,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

    }
}

fun NavHostController.navigateToSingleTop(router: String) {
    navigate(router) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}