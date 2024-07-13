package com.rijalmyd.wanatik.ui.screen.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rijalmyd.wanatik.ui.navigation.ScreenSearch

fun NavGraphBuilder.searchScreenRoute(navController: NavController) {
    composable<ScreenSearch> { 
        SearchScreen(navController = navController)
    }
}