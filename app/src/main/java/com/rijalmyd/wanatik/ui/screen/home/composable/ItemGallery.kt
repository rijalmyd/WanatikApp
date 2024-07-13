package com.rijalmyd.wanatik.ui.screen.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rijalmyd.wanatik.ui.theme.WanatikTheme

@Composable
fun ItemGallery(
    name: String,
    imageUrl: String,
    onClick: () -> Unit,
    height: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .height(height)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable(onClick = onClick)
                .height(height)
                .fillMaxWidth()
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0x56000000))
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemGalleryPreview() {
    WanatikTheme {
        ItemGallery(
            name = "Kawung",
            imageUrl = "",
            onClick = { /*TODO*/ },
            height = 200.dp
        )
    }
}


