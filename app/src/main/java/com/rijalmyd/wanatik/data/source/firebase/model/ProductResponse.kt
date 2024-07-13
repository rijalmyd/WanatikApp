package com.rijalmyd.wanatik.data.source.firebase.model

data class ProductResponse(
    val id: String = "",
    val storeId: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val price: Long = 0L,
    val discount: Int = 0,
    val motifName: String = "",
)