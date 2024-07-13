package com.rijalmyd.wanatik.ui.screen.detail_image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailImage

@Composable
fun DetailImageScreen(
    image: ScreenDetailImage,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            Color.Black,
        )
        systemUiController.setNavigationBarColor(
            Color.Black,
        )
        systemUiController.setStatusBarColor(
            Color.Black,
        )
        onDispose {
            systemUiController.setSystemBarsColor(
                Color.White,
            )
            systemUiController.setNavigationBarColor(
                Color.White,
            )
            systemUiController.setStatusBarColor(
                Color.White,
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AsyncImage(
            model = image.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.align(Alignment.TopEnd)
                .padding(8.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}