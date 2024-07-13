package com.rijalmyd.wanatik.ui.screen.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rijalmyd.wanatik.ui.navigation.ScreenHistory

fun NavGraphBuilder.detailHistoryScreenRoute(navController: NavController) {
    composable<ScreenHistory> {
        val history = it.toRoute<ScreenHistory>()
        DetailHistoryScreen(
            id = history.id,
            navController = navController
        )
    }
}