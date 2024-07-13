package com.rijalmyd.wanatik.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rijalmyd.wanatik.ui.navigation.BottomNavigationBar
import com.rijalmyd.wanatik.ui.navigation.BottomNavigationItem
import com.rijalmyd.wanatik.ui.navigation.Screen
import com.rijalmyd.wanatik.ui.navigation.WanatikNavGraph

@Composable
fun WanatikApp(
    navController: NavHostController = rememberNavController()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(Color.White)
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = currentRoute.shouldShowBottomBar(),
                enter = slideInVertically {
                    40
                }
            ) {
                BottomNavigationBar(
                    items = listOf(
                        BottomNavigationItem(
                            title = "Home",
                            icon = Icons.Outlined.Home,
                            screen = Screen.Home
                        ),
                        BottomNavigationItem(
                            title = "Scan Motif",
                            icon = Icons.Outlined.CameraAlt,
                            screen = Screen.Scan
                        ),
                        BottomNavigationItem(
                            title = "Profile",
                            icon = Icons.Outlined.PersonOutline,
                            screen = Screen.Profile
                        )
                    ),
                    navController = navController
                )
            }
        }
    ) { contentPadding ->
        WanatikNavGraph(
            navController = navController,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

private fun String?.shouldShowBottomBar(): Boolean {
    return this in setOf(
        Screen.Home.route,
        Screen.Profile.route
    )
}