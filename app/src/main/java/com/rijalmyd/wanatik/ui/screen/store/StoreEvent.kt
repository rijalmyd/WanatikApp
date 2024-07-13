package com.rijalmyd.wanatik.ui.screen.store

sealed class StoreEvent {
    data class OnGetProducts(
        val storeId: String
    ) : StoreEvent()
}
