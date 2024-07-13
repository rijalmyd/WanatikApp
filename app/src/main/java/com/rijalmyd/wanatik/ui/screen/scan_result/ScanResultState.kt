package com.rijalmyd.wanatik.ui.screen.scan_result

import com.rijalmyd.wanatik.data.model.Product

data class ScanResultState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList()
)
