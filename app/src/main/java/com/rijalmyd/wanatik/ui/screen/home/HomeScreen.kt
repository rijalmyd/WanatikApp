package com.rijalmyd.wanatik.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.rijalmyd.wanatik.R
import com.rijalmyd.wanatik.data.model.Product
import com.rijalmyd.wanatik.data.model.Store
import com.rijalmyd.wanatik.ui.components.ItemProduct
import com.rijalmyd.wanatik.ui.components.ItemStoreCircle
import com.rijalmyd.wanatik.ui.navigation.Screen
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailProduct
import com.rijalmyd.wanatik.ui.navigation.ScreenListAll
import com.rijalmyd.wanatik.ui.navigation.ScreenMotif
import com.rijalmyd.wanatik.ui.navigation.ScreenNotification
import com.rijalmyd.wanatik.ui.navigation.ScreenSearch
import com.rijalmyd.wanatik.ui.navigation.ScreenStoreDetail
import com.rijalmyd.wanatik.ui.screen.home.composable.HomeShimmer
import com.rijalmyd.wanatik.ui.screen.home.composable.ItemGallery
import com.rijalmyd.wanatik.ui.screen.home.composable.MySearchBar
import com.rijalmyd.wanatik.ui.screen.list_all.ListAllType

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        user = state.user,
        refreshing = state.isLoading,
        popularProducts = state.popularProducts,
        nearStores = state.nearStores,
        onProductClick = {
            viewModel.onEvent(HomeEvent.OnProductClick(it.id))
            navController.navigate(ScreenDetailProduct(it.id))
        },
        onStoreClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set("store", it)
            navController.navigate(ScreenStoreDetail)
        },
        onRefresh = { viewModel.onEvent(HomeEvent.OnRefresh) },
        onNotificationClick = { navController.navigate(ScreenNotification) },
        onScanClick = { navController.navigate(Screen.Scan.route) },
        onSearchClick = { navController.navigate(ScreenSearch) },
        onMotifClick = { id, name, image ->
            navController.navigate(
                ScreenMotif(
                    motifId = id,
                    motifName = name,
                    motifImage = image
                )
            )
        },
        onSeeMoreClick = {
            navController.navigate(ScreenListAll(if (it == ListAllType.PRODUCT) 0 else 1))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    user: FirebaseUser?,
    refreshing: Boolean,
    popularProducts: List<Product>,
    nearStores: List<Store>,
    onProductClick: (Product) -> Unit,
    onStoreClick: (Store) -> Unit,
    onScanClick: () -> Unit,
    onSearchClick: () -> Unit,
    onRefresh: () -> Unit,
    onNotificationClick: () -> Unit,
    onMotifClick: (id: String, name: String, image: String) -> Unit,
    onSeeMoreClick: (ListAllType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hi, ${
                            user?.displayName?.split(" ")
                                ?.firstOrNull() ?: user?.displayName ?: "Welcome!"
                        }"
                    )
                },
                actions = {
                    IconButton(onClick = onNotificationClick) {
                        Icon(
                            imageVector = Icons.Outlined.NotificationsNone,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { contentPadding ->
        val refreshingState = rememberPullToRefreshState()

        PullToRefreshBox(
            modifier = Modifier.padding(contentPadding),
            isRefreshing = refreshing,
            state = refreshingState,
            onRefresh = onRefresh
        ) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                MySearchBar(
                    onSearchClick = onSearchClick,
                    onScanClick = onScanClick,
                    modifier = Modifier.padding(16.dp)
                )

                if (refreshing) {
                    HomeShimmer(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    Column(Modifier.padding(horizontal = 16.dp)) {
                        AsyncImage(
                            model = R.drawable.ic_banner,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.small)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { onSeeMoreClick(ListAllType.PRODUCT) }
                        ) {
                            Text(
                                text = "Populer",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                contentDescription = null
                            )
                        }
                    }
                    LazyRow(
                        contentPadding = PaddingValues(16.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(items = popularProducts, key = { it.id }) {
                            ItemProduct(
                                store = it.store,
                                title = it.title,
                                imageUrl = it.imageUrl,
                                price = it.price,
                                discount = it.discount,
                                onProductClick = { onProductClick(it) }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .clickable { onSeeMoreClick(ListAllType.STORE) }
                            .padding(top = 24.dp, end = 16.dp, start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Toko terdekat",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = null
                        )
                    }
                    LazyRow(
                        contentPadding = PaddingValues(16.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(items = nearStores, key = { it.id }) {
                            ItemStoreCircle(
                                name = it.name,
                                imageUrl = it.imageUrl,
                                onStoreClick = { onStoreClick(it) }
                            )
                        }
                    }
                    Column(Modifier.padding(vertical = 24.dp, horizontal = 16.dp)) {
                        Text(
                            text = "Kategori motif",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        MotifGallery(
                            modifier = Modifier.fillMaxWidth(),
                            onMotifClick = onMotifClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MotifGallery(
    onMotifClick: (id: String, name: String, image: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        val (caricaRef, purwacengRef, sidomuktiRef, sekarjagadRef, kawungRef) = createRefs()

        ItemGallery(
            name = "Sidomukti",
            imageUrl = "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/d5d0870b-4402-4177-8a3c-06ba3091179c.jpg",
            onClick = {
                onMotifClick(
                    "sidomukti",
                    "Motif Sidomukti",
                    "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/d5d0870b-4402-4177-8a3c-06ba3091179c.jpg"
                )
            },
            height = 256.dp,
            modifier = Modifier
                .constrainAs(sidomuktiRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(caricaRef.start, 4.dp)
                    width = Dimension.fillToConstraints
                }
        )
        ItemGallery(
            name = "Purwaceng",
            imageUrl = "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/purwaceng.jpg",
            onClick = {
                onMotifClick(
                    "purwaceng",
                    "Motif Purwaceng",
                    "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/purwaceng.jpg"
                )
            },
            height = 144.dp,
            modifier = Modifier
                .constrainAs(purwacengRef) {
                    top.linkTo(sidomuktiRef.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(sidomuktiRef.end)
                    width = Dimension.fillToConstraints
                }
        )
        ItemGallery(
            name = "Carica",
            imageUrl = "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/56252739_2299348976979777_1501952593146937344_n.jpg",
            onClick = {
                onMotifClick(
                    "carica",
                    "Motif Carica",
                    "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/56252739_2299348976979777_1501952593146937344_n.jpg"
                )
            },
            height = 112.dp,
            modifier = Modifier
                .constrainAs(caricaRef) {
                    top.linkTo(parent.top)
                    start.linkTo(sidomuktiRef.end, 4.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
        ItemGallery(
            name = "Kawung",
            imageUrl = "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/filosofi-motif-Batik-Kawung.webp",
            onClick = {
                onMotifClick(
                    "kawung",
                    "Motif Kawung",
                    "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/filosofi-motif-Batik-Kawung.webp"
                )
            },
            height = 160.dp,
            modifier = Modifier
                .constrainAs(kawungRef) {
                    top.linkTo(caricaRef.bottom, 8.dp)
                    start.linkTo(caricaRef.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
        ItemGallery(
            name = "Sekarjagad",
            imageUrl = "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/Batik-Sekar-Jagad-Warna-Coklat-_-Sumber_-Freepik.jpg",
            onClick = {
                onMotifClick(
                    "sekarjagad",
                    "Motif Sekarjagad",
                    "https://raw.githubusercontent.com/rijalmyd/Wanatik/main/Batik-Sekar-Jagad-Warna-Coklat-_-Sumber_-Freepik.jpg"
                )
            },
            height = 120.dp,
            modifier = Modifier
                .constrainAs(sekarjagadRef) {
                    top.linkTo(kawungRef.bottom, 8.dp)
                    start.linkTo(caricaRef.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }
}