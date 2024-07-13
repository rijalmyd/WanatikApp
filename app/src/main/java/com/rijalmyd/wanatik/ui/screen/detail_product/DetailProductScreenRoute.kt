package com.rijalmyd.wanatik.ui.screen.detail_product

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailProduct

fun NavGraphBuilder.detailProductScreenRoute(navController: NavController) {
    composable<ScreenDetailProduct> {
        val product = it.toRoute<ScreenDetailProduct>()
        DetailProductScreen(
            productId = product.id,
            navController = navController
        )
    }
}