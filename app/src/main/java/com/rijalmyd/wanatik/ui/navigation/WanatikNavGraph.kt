package com.rijalmyd.wanatik.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rijalmyd.wanatik.ui.screen.detail_image.detailImageScreenRoute
import com.rijalmyd.wanatik.ui.screen.detail_product.detailProductScreenRoute
import com.rijalmyd.wanatik.ui.screen.history.detailHistoryScreenRoute
import com.rijalmyd.wanatik.ui.screen.home.homeScreenRoute
import com.rijalmyd.wanatik.ui.screen.list_all.listAllScreenRoute
import com.rijalmyd.wanatik.ui.screen.motif.motifScreenRoute
import com.rijalmyd.wanatik.ui.screen.notification.notificationScreenRoute
import com.rijalmyd.wanatik.ui.screen.profile.profileScreenRoute
import com.rijalmyd.wanatik.ui.screen.scan.scanScreenRoute
import com.rijalmyd.wanatik.ui.screen.scan_result.scanResultScreenRoute
import com.rijalmyd.wanatik.ui.screen.search.searchScreenRoute
import com.rijalmyd.wanatik.ui.screen.store.storeScreenRoute

@Composable
fun WanatikNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        homeScreenRoute(navController)
        listAllScreenRoute(navController)
        notificationScreenRoute(navController)
        scanScreenRoute(navController)
        scanResultScreenRoute(navController)
        profileScreenRoute(navController)
        detailProductScreenRoute(navController)
        detailHistoryScreenRoute(navController)
        detailImageScreenRoute(navController)
        storeScreenRoute(navController)
        motifScreenRoute(navController)
        searchScreenRoute(navController)
    }
}