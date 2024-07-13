package com.rijalmyd.wanatik.ui.screen.scan_result

sealed class ScanResultEvent {
    data class OnGetProducts(
        val classDetected: String
    ) : ScanResultEvent()
}