package com.rijalmyd.wanatik.ui.screen.detail_image

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailImage

fun NavGraphBuilder.detailImageScreenRoute(navController: NavController) {
    composable<ScreenDetailImage> {
        val detail = it.toRoute<ScreenDetailImage>()
        DetailImageScreen(
            image = detail,
            navController = navController
        )
    }
}