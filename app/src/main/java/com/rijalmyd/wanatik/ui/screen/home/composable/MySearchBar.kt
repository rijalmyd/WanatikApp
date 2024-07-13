package com.rijalmyd.wanatik.ui.screen.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rijalmyd.wanatik.ui.theme.WanatikTheme
import kotlinx.coroutines.delay

@Composable
fun MySearchBar(
    onSearchClick: () -> Unit,
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val queryText = listOf(
        "Cari motif batik",
        "Motif carica",
        "Motif purwaceng",
        "Batik wonosobo",
        "Motif sidomukti"
    )
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(8000)
            currentIndex = (currentIndex + 1) % queryText.size
        }
    }

    var displayedText by remember { mutableStateOf("") }
    LaunchedEffect(currentIndex) {
        displayedText = ""
        queryText[currentIndex].forEachIndexed { _, char ->
            delay(200)
            displayedText += char
        }
    }

    Surface(
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .heightIn(max = 64.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(onClick = onSearchClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                )
                Text(text = displayedText, maxLines = 1)
            }
            VerticalDivider(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier
                    .clickable(onClick = onScanClick)
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(
                    onClick = onScanClick,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Icon(imageVector = Icons.Outlined.CameraAlt, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySearchBarPreview() {
    WanatikTheme {
        MySearchBar(
            onScanClick = {},
            onSearchClick = {},
        )
    }
}