package com.rijalmyd.wanatik.ui.screen.list_all

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rijalmyd.wanatik.ui.navigation.ScreenListAll
import com.rijalmyd.wanatik.ui.navigation.ScreenNotification

fun NavGraphBuilder.listAllScreenRoute(navController: NavController) {
    composable<ScreenListAll> {
        val listAll = it.toRoute<ScreenListAll>()
        ListAllScreen(
            type = if (listAll.type == 0) ListAllType.PRODUCT else ListAllType.STORE,
            navController = navController
        )
    }
}