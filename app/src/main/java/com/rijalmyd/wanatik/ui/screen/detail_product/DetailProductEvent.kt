package com.rijalmyd.wanatik.ui.screen.detail_product

sealed class DetailProductEvent {
    data class OnGetDetailProduct(
        val id: String
    ) : DetailProductEvent()
}
