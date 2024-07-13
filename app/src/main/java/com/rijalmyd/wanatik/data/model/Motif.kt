package com.rijalmyd.wanatik.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Motif(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val confidence: Double = 0.0
) : Parcelable