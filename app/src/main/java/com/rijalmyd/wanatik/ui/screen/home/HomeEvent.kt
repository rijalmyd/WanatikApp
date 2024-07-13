package com.rijalmyd.wanatik.ui.screen.home

sealed class HomeEvent {
    data class OnProductClick(
        val productId: String
    ) : HomeEvent()
    data object OnRefresh : HomeEvent()
}