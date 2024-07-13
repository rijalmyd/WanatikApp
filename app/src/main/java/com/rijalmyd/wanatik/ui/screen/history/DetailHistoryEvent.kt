package com.rijalmyd.wanatik.ui.screen.history

sealed class DetailHistoryEvent {
    data class OnGetHistory(
        val id: String
    ) : DetailHistoryEvent()
    data class OnGetProducts(
        val classDetected: String
    ) : DetailHistoryEvent()
}
