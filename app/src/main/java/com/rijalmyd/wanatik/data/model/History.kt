package com.rijalmyd.wanatik.data.model

data class History(
    val id: String = "",
    val imageUrl: String = "",
    val motifs: List<Motif?> = emptyList(),
    val userId: String = "",
    val date: String = "",
)
