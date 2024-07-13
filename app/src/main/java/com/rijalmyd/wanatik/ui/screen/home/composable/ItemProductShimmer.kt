package com.rijalmyd.wanatik.ui.screen.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
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
fun ItemProductShimmer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(144.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(144.dp)
                    .background(
                        shimmerBrush(), MaterialTheme.shapes.small.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
                    .clip(
                        MaterialTheme.shapes.small.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            modifier = Modifier
                .width(100.dp)
                .background(shimmerBrush())
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}