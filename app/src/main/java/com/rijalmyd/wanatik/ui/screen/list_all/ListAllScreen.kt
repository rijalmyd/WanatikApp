package com.rijalmyd.wanatik.ui.screen.list_all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rijalmyd.wanatik.ui.components.ItemProductGrid
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailProduct
import com.rijalmyd.wanatik.ui.navigation.ScreenStoreDetail
import com.rijalmyd.wanatik.ui.screen.list_all.composable.ItemStoreGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAllScreen(
    type: ListAllType,
    navController: NavController,
    viewModel: ListAllViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(ListAllEvent.OnGetList(type))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (type == ListAllType.STORE) "Toko Terdekat" else "Produk Terpopuler") },
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

        if (state.isLoading) {
            Box(
               modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(contentPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.products.isNotEmpty()) {
                items(items = state.products, key = { it.id }) {
                    ItemProductGrid(
                        title = it.title,
                        imageUrl = it.imageUrl,
                        price = it.price,
                        discount = it.discount,
                        onProductClick = { navController.navigate(ScreenDetailProduct(it.id)) }
                    )
                }
            }
            if (state.stores.isNotEmpty()) {
                items(items = state.stores, key = { it.id }) {
                    ItemStoreGrid(
                        name = it.name,
                        imageUrl = it.imageUrl,
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("store", it)
                            navController.navigate(ScreenStoreDetail)
                        }
                    )
                }
            }
        }
    }
}
