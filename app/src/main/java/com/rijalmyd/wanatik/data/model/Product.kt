package com.rijalmyd.wanatik.data.model

data class Product(
    val id: String = "",
    val store: Store = Store(),
    val title: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val price: Long = 0L,
    val discount: Int = 0,
    val motifName: String = "",
    val clicked: Int = 0,
)