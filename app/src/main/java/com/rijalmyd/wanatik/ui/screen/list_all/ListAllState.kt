package com.rijalmyd.wanatik.ui.screen.list_all

import com.rijalmyd.wanatik.data.model.Product
import com.rijalmyd.wanatik.data.model.Store

data class ListAllState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isError: Boolean = false,
    val products: List<Product> = emptyList(),
    val stores: List<Store> = emptyList(),
)