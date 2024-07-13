package com.rijalmyd.wanatik.ui.screen.detail_product

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rijalmyd.wanatik.data.model.Store
import com.rijalmyd.wanatik.ui.components.ExpandableText
import com.rijalmyd.wanatik.ui.navigation.ScreenDetailImage
import com.rijalmyd.wanatik.ui.navigation.ScreenStoreDetail
import com.rijalmyd.wanatik.ui.screen.detail_product.composable.CardStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProductScreen(
    productId: String,
    navController: NavController,
    viewModel: DetailProductViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(DetailProductEvent.OnGetDetailProduct(productId))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Produk") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    openWhatsAppChat(
                        context,
                        state.product?.store?.phoneNumber ?: return@ExtendedFloatingActionButton,
                        message = "Saya tertarik untuk membeli ${state.product?.title}"
                    )
//                    scope.launch {
//                        val bitmap = preloadImage(state.product?.imageUrl ?: "", context)
//                        shareViaWhatsApp(
//                            context,
//                            image = bitmap,
//                            text = "Saya tertarik untuk membeli ${state.product?.title}",
//                            phoneNumber = state.product?.store?.phoneNumber ?: "",
//                        )
//                    }
                },
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Call,
                        contentDescription = null
                    )
                },
                text = { Text(text = "Beli") }
            )
        }
    ) { contentPadding ->
        if (state.product != null) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                AsyncImage(
                    model = state.product?.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .fillMaxWidth()
                        .height(256.dp)
                        .clickable {
                            navController.navigate(ScreenDetailImage(state.product?.imageUrl ?: ""))
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Rp${state.product!!.price - (state.product!!.price * state.product!!.discount / 100)}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rp${state.product?.price}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${state.product?.discount}%",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFF54949),
                            fontWeight = FontWeight.Bold
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.product?.title ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Deskripsi produk",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                ExpandableText(
                    text = state.product?.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                CardStore(
                    store = state.product?.store ?: Store(),
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set("store", state.product?.store ?: Store())
                            navController.navigate(ScreenStoreDetail)
                        }
                )
            }
        }
    }
}

private fun getBitmapUriFromBitmap(context: Context, bitmap: Bitmap): Uri? {
    var bmpUri: Uri? = null
    try {
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "share_image_${System.currentTimeMillis()}.png"
        )
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.close()
        bmpUri = FileProvider.getUriForFile(context, "com.rijalmyd.wanatik.fileprovider", file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}

fun openWhatsAppChat(context: Context, toNumber: String, message: String) {
    val url = "https://api.whatsapp.com/send?phone=$toNumber&text=" + URLEncoder.encode(
        message,
        "UTF-8"
    )
    try {
        context.packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
        context.startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) })
    } catch (e: PackageManager.NameNotFoundException) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}