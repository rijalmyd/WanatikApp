package com.rijalmyd.wanatik.ui.screen.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijalmyd.wanatik.data.repository.UserRepository
import com.rijalmyd.wanatik.data.source.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        getUser()
        getHistories()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnSignInGoogleWithIntent -> signInGoogle(event.context)
            is ProfileEvent.SetLoginLoadingState -> _state.update {
                it.copy(
                    isLoginLoading = event.isLoading
                )
            }
            is ProfileEvent.OnLogout -> logout()
        }
    }

    private fun getHistories() = viewModelScope.launch {
        userRepository.getHistories().collect { result ->
            when (result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> _state.update {
                    it.copy(
                        histories = result.data
                    )
                }
            }
        }
    }

    private fun logout() = viewModelScope.launch {
        userRepository.signOut()
        _state.update {
            it.copy(
                user = null,
                histories = emptyList()
            )
        }
    }

    private fun signInGoogle(context: Context) = viewModelScope.launch {
        userRepository.signInGoogle(context).collect {  result ->
            when (result) {
                is  Result.Error-> _state.update {
                    it.copy(
                        isLoginLoading = false,
                    )
                }
                is Result.Loading -> _state.update {
                    it.copy(
                        isLoginLoading = true
                    )
                }
                is Result.Success -> {
                    getHistories()
                    _state.update {
                        it.copy(
                            isLoginLoading = false,
                            user = result.data.user
                        )
                    }
                }
            }
        }
    }

    private fun getUser() = viewModelScope.launch {
        userRepository.getSignedUser().collect { user ->
            _state.update {
                it.copy(
                    user = user
                )
            }
        }
    }
}