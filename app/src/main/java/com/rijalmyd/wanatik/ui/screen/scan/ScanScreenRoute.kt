package com.rijalmyd.wanatik.ui.screen.scan

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rijalmyd.wanatik.ui.navigation.Screen

fun NavGraphBuilder.scanScreenRoute(navController: NavController) {
    composable(Screen.Scan.route) {
        ScanScreen(navController = navController)
    }
}