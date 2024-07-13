package com.rijalmyd.wanatik.ui.screen.profile

import android.content.Context

sealed class ProfileEvent {
    data class OnSignInGoogleWithIntent(
        val context: Context
    ) : ProfileEvent()
    data class SetLoginLoadingState(
        val isLoading: Boolean
    ) : ProfileEvent()
    data object OnLogout : ProfileEvent()
}
