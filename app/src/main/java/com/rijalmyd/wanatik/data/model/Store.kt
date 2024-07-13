package com.rijalmyd.wanatik.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Store(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val instagram: String = "",
) : Parcelable