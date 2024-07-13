package com.rijalmyd.wanatik.ui.screen.motif

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rijalmyd.wanatik.ui.components.ExpandableText
import com.rijalmyd.wanatik.ui.components.ItemProductGrid
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailImage
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailProduct
import com.rijalmyd.wanatik.ui.navigation.ScreenMotif
import com.rijalmyd.wanatik.ui.screen.scan_result.composable.ItemImageMotif

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotifScreen(
    screenMotif: ScreenMotif,
    navController: NavController,
    viewModel: MotifViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(MotifEvent.OnGetMotifDetail(screenMotif.motifId))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detail Motif")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(contentPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Column(Modifier) {
                    AsyncImage(
                        model = screenMotif.motifImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(224.dp)
                            .clip(MaterialTheme.shapes.small)
                            .clickable {
                                navController.navigate(ScreenDetailImage(screenMotif.motifImage))
                            }
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        state.motif?.images?.forEach {
                            ItemImageMotif(
                                imageUrl = it,
                                onItemClick = { navController.navigate(ScreenDetailImage(it)) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.motif?.name ?: "",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ExpandableText(
                        text = state.motif?.description ?: "",
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Produk terkait",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                    )
                }
            }
            items(items = state.products, key = { it.id }) {
                ItemProductGrid(
                    store = it.store,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    price = it.price,
                    discount = it.discount,
                    onProductClick = { navController.navigate(ScreenDetailProduct(it.id)) }
                )
            }
        }
    }
}