package com.rijalmyd.wanatik.ui.screen.store

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rijalmyd.wanatik.data.model.Store
import com.rijalmyd.wanatik.ui.navigation.ScreenStoreDetail

fun NavGraphBuilder.storeScreenRoute(navController: NavController) {
    composable<ScreenStoreDetail> {
        val store = navController.previousBackStackEntry?.savedStateHandle?.get<Store>("store")
        if (store != null) {
            StoreScreen(
                store = store,
                navController = navController
            )
        }
    }
}