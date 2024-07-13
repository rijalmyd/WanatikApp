package com.rijalmyd.wanatik.ui.screen.search

import com.rijalmyd.wanatik.data.model.Product

data class SearchState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val query: String = "",
    val products: List<Product> = emptyList(),
)