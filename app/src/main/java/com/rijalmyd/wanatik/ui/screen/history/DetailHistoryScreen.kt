package com.rijalmyd.wanatik.ui.screen.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
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
import com.rijalmyd.wanatik.ui.components.ItemMotif
import com.rijalmyd.wanatik.ui.components.ItemProduct
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailImage
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailProduct
import com.rijalmyd.wanatik.ui.screen.scan_result.composable.ItemImageMotif

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHistoryScreen(
    id: String,
    navController: NavController,
    viewModel: DetailHistoryViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(DetailHistoryEvent.OnGetHistory(id))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.history) {
        viewModel.onEvent(
            DetailHistoryEvent.OnGetProducts(
                state.history?.motifs?.getOrNull(0)?.name?.replace(
                    "Motif ",
                    ""
                )?.lowercase() ?: ""
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Riwayat Klasifikasi")
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
        LazyColumn(
            modifier = Modifier.padding(contentPadding),
        ) {
            item {
                Column(Modifier) {
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = state.history?.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(224.dp)
                            .clip(MaterialTheme.shapes.small)
                            .clickable {
                                navController.navigate(ScreenDetailImage(state.history?.imageUrl ?: ""))
                            }
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        state.history?.motifs?.getOrNull(0)?.images?.forEach {
                            ItemImageMotif(
                                imageUrl = it,
                                onItemClick = { navController.navigate(ScreenDetailImage(it)) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.history?.motifs?.getOrNull(0)?.name ?: "",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Confidence : ${state.history?.motifs?.getOrNull(0)?.confidence}%",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ExpandableText(
                        text = state.history?.motifs?.getOrNull(0)?.description ?: "",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            if (state.products.isNotEmpty()) {
                item {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Produk terkait",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items = state.products, key = { it.id }) {
                                ItemProduct(
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
            }
            if ((state.history?.motifs?.size ?: 1) > 1) {
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Hasil lainnya",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                items = state.history?.motifs?.drop(1) ?: emptyList(),
                                key = { it?.id ?: "" }) {
                                ItemMotif(
                                    motif = it,
                                    onItemClick = { /*TODO*/ },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}