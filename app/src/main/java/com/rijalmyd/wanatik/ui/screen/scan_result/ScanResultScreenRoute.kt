package com.rijalmyd.wanatik.ui.screen.scan_result

import android.net.Uri
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rijalmyd.wanatik.data.model.Motif
import com.rijalmyd.wanatik.ui.navigation.ScreenScanResult

fun NavGraphBuilder.scanResultScreenRoute(navController: NavController) {
    composable<ScreenScanResult>(
        enterTransition = {
            scaleIn()
        },
        exitTransition = {
            scaleOut()
        }
    ) {
        val classifyResults = navController.previousBackStackEntry?.savedStateHandle?.get<List<Motif?>>("result")
        val imageUri = navController.previousBackStackEntry?.savedStateHandle?.get<Uri>("image_uri")
        if (classifyResults != null && imageUri != null) {
            ScanResultScreen(
                classifyResults = classifyResults,
                imageUri = imageUri,
                navController = navController
            )
        }
    }
}