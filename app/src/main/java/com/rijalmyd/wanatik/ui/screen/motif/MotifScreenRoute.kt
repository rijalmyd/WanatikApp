package com.rijalmyd.wanatik.ui.screen.motif

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rijalmyd.wanatik.ui.navigation.ScreenMotif

fun NavGraphBuilder.motifScreenRoute(navController: NavController) {
    composable<ScreenMotif> {
        val screenMotif = it.toRoute<ScreenMotif>()
        MotifScreen(
            screenMotif = screenMotif,
            navController = navController
        )
    }
}