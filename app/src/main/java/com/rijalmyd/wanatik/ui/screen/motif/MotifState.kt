package com.rijalmyd.wanatik.ui.screen.motif

import com.rijalmyd.wanatik.data.model.Motif
import com.rijalmyd.wanatik.data.model.Product

data class MotifState(
    val isLoading: Boolean = false,
    val motif: Motif? = null,
    val products: List<Product> = emptyList(),
)