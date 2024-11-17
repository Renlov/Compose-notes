package com.p1.composenote.ui.navigation

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home")
    data object Detail: Screen(route = "detail")
    data object Bookmark: Screen(route = "bookmark")
}