package com.rijalmyd.wanatik.ui.screen.profile

import com.google.firebase.auth.FirebaseUser
import com.rijalmyd.wanatik.data.model.History

data class ProfileState(
    val user: FirebaseUser? = null,
    val isLoginLoading: Boolean = false,
    val histories: List<History> = emptyList()
)