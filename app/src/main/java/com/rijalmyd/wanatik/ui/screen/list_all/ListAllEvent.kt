package com.rijalmyd.wanatik.ui.screen.list_all

sealed class ListAllEvent {
    data class OnGetList(
        val type: ListAllType
    ) : ListAllEvent()
}