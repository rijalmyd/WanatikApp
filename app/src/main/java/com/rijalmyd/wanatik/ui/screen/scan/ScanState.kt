package com.rijalmyd.wanatik.ui.screen.scan

import android.net.Uri
import com.rijalmyd.wanatik.data.model.Motif

data class ScanState(
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val loadingProgress: String? = null,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val classifyResult: List<Motif?> = emptyList(),
    val imageUri: Uri? = null,
)