package com.rijalmyd.wanatik.ui.screen.scan

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.rijalmyd.wanatik.R
import com.rijalmyd.wanatik.ui.navigation.ScreenScanResult
import com.rijalmyd.wanatik.ui.screen.scan.composable.captureImage
import com.rijalmyd.wanatik.ui.screen.scan.composable.getCameraProvider
import com.rijalmyd.wanatik.ui.screen.scan.composable.toFile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    navController: NavController,
    viewModel: ScanViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    state.errorMessage?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    if (state.classifyResult.isNotEmpty()) {
        LaunchedEffect(key1 = Unit) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "result",
                state.classifyResult
            )
            navController.currentBackStackEntry?.savedStateHandle?.set("image_uri", state.imageUri)
            Log.d("IMAGE_URIII", "ScanScreen: ${state.imageUri}")
            navController.navigate(ScreenScanResult)
            viewModel.onEvent(ScanEvent.ResetState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Scan Motif") },
            )
        }
    ) { contentPadding ->
        CameraView(
            onImageGet = {
                viewModel.onEvent(ScanEvent.OnSaveImageUri(it))
                val imageFile = it?.toFile(context)
                if (imageFile != null) {
                    viewModel.onEvent(ScanEvent.OnScanUpload(imageFile, it))
                } else {
                    Toast.makeText(context, "Gagal memproses gambar!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(contentPadding)
        )

        if (state.isLoading) {
            AlertDialog(
                onDismissRequest = { /*TODO*/ },
                confirmButton = { /*TODO*/ },
                title = { Text(text = "Memproses...") },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = "${state.loadingProgress?.let { "(${state.loadingProgress})" } ?: ""} ${state.loadingMessage}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.onEvent(ScanEvent.OnCancelClassify) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "Batal")
                        }
                    }
                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    onImageGet: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val launcherIntentGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = onImageGet
    )

    LaunchedEffect(key1 = Unit) {
        if (!cameraPermissionState.hasPermission) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (!cameraPermissionState.hasPermission) {
        Text(text = "No Camera Permission!")
        return
    }

    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder()
        .build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember { ImageCapture.Builder().build() }
    var hasFlashUnit by remember { mutableStateOf(false) }
    var flashOn by remember { mutableStateOf(false) }

    LaunchedEffect(lensFacing, flashOn) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        val camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)

        hasFlashUnit = camera.cameraInfo.hasFlashUnit()
        if (hasFlashUnit) camera.cameraControl.enableTorch(flashOn)
    }

    Column(modifier) {
        Box(modifier = Modifier.weight(1f)) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize(),
            )
            HintText(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = {
                    launcherIntentGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = { captureImage(imageCapture, context, onImageGet) },
                modifier = Modifier.size(80.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_shutter),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            IconButton(
                onClick = { flashOn = !flashOn },
                enabled = hasFlashUnit
            ) {
                Icon(
                    imageVector = if (!flashOn) Icons.Outlined.FlashOff else Icons.Outlined.FlashOn,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun HintText(
    modifier: Modifier = Modifier,
    text: String = "Arahkan kamera ke motif\nbatik untuk mengklasifikasi"
) {
    Box(
        modifier = modifier
            .background(Color(0x75000000), MaterialTheme.shapes.medium)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}