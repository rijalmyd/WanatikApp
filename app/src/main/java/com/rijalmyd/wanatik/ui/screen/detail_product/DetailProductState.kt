package com.rijalmyd.wanatik.ui.screen.detail_product

import com.rijalmyd.wanatik.data.model.Product

data class DetailProductState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val product: Product? = null
)