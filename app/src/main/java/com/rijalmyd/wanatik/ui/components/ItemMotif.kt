package com.rijalmyd.wanatik.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rijalmyd.wanatik.data.model.Motif
import com.rijalmyd.wanatik.ui.theme.WanatikTheme

@Composable
fun ItemMotif(
    motif: Motif?,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable(onClick = onItemClick)
            .width(144.dp)
    ) {
        AsyncImage(
            model = motif?.images?.getOrNull(0),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(144.dp)
                .clip(
                    MaterialTheme.shapes.small.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${motif?.name}\n${motif?.confidence}%",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemMotifPreview() {
    WanatikTheme {
        ItemMotif(
            motif = Motif(
                name = "Motif Carica",
                confidence = 54.0,
                images = listOf(
                    ""
                )
            ),
            onItemClick = { /*TODO*/ }
        )
    }
}