package com.rijalmyd.wanatik.data.model

import com.google.firebase.auth.FirebaseUser

data class SignInResult(
    val user: FirebaseUser?,
    val errorMessage: String?,
)