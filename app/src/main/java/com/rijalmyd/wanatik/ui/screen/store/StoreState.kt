package com.rijalmyd.wanatik.ui.screen.store

import com.rijalmyd.wanatik.data.model.Product

data class StoreState(
    val products: List<Product> = emptyList()
)
