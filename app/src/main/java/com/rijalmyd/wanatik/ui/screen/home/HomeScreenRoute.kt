package com.rijalmyd.wanatik.ui.screen.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rijalmyd.wanatik.ui.navigation.Screen

fun NavGraphBuilder.homeScreenRoute(navController: NavController) {
    composable(Screen.Home.route) {
        HomeScreen(navController = navController)
    }
}