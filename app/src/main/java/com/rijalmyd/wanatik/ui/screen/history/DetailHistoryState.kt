package com.rijalmyd.wanatik.ui.screen.history

import com.rijalmyd.wanatik.data.model.History
import com.rijalmyd.wanatik.data.model.Product

data class DetailHistoryState(
    val isLoading: Boolean = false,
    val history: History? = null,
    val products: List<Product> = emptyList()
)
