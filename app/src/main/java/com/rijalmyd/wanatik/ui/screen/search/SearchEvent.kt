package com.rijalmyd.wanatik.ui.screen.search

sealed class SearchEvent {
    data class OnQueryTextChange(
        val query: String
    ) : SearchEvent()
    data class OnSearch(
        val query: String
    ) : SearchEvent()
}