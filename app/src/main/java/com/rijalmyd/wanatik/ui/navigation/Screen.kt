package com.rijalmyd.wanatik.ui.navigation

import com.rijalmyd.wanatik.ui.screen.list_all.ListAllType
import kotlinx.serialization.Serializable

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Scan : Screen("scan")
    data object Profile : Screen("profile")
}

@Serializable
object ScreenNotification

@Serializable
data class ScreenListAll(
    val type: Int = 0
)

@Serializable
object ScreenScanResult

@Serializable
data class ScreenDetailProduct(
    val id: String
)

@Serializable
data class ScreenHistory(
    val id: String
)

@Serializable
data class ScreenDetailImage(
    val image: String?,
)

@Serializable
object ScreenStoreDetail

@Serializable
object ScreenSearch

@Serializable
data class ScreenMotif(
    val motifName: String,
    val motifImage: String,
    val motifId: String,
)