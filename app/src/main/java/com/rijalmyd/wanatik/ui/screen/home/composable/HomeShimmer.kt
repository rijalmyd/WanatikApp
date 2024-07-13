package com.rijalmyd.wanatik.ui.screen.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rijalmyd.wanatik.ui.components.shimmerBrush

@Composable
fun HomeShimmer(
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        AsyncImage(
            model = null,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .background(shimmerBrush(), MaterialTheme.shapes.small)
                .height(200.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .width(200.dp)
                    .background(shimmerBrush())
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            ItemProductShimmer()
            Spacer(modifier = Modifier.width(8.dp))
            ItemProductShimmer()
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .width(200.dp)
                .background(shimmerBrush())
        )
    }
}

