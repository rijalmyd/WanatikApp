package com.rijalmyd.wanatik.ui.screen.motif

sealed class MotifEvent {
    data class OnGetMotifDetail(val id: String) : MotifEvent()
}