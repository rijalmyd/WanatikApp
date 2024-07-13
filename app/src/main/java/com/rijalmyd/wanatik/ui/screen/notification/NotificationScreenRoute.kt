package com.rijalmyd.wanatik.ui.screen.notification

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rijalmyd.wanatik.ui.navigation.ScreenNotification

fun NavGraphBuilder.notificationScreenRoute(navController: NavController) {
    composable<ScreenNotification> {
        NotificationScreen(navController)
    }
}