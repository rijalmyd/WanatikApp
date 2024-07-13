package com.rijalmyd.wanatik.ui.screen.home

import com.google.firebase.auth.FirebaseUser
import com.rijalmyd.wanatik.data.model.Product
import com.rijalmyd.wanatik.data.model.Store

data class HomeState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val popularProducts: List<Product> = emptyList(),
    val nearStores: List<Store> = emptyList(),
    val user: FirebaseUser? = null,
)