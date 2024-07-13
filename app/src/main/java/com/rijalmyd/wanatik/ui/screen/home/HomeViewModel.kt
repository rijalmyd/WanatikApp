package com.rijalmyd.wanatik.ui.screen.home

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
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        init()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnProductClick -> onProductClick(event.productId)
            is HomeEvent.OnRefresh -> init()
        }
    }

    private fun init() {
        getPopularProducts()
        getNearStores()
        getUser()
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

    private fun onProductClick(productId: String) = viewModelScope.launch {
        userRepository.clickedProduct(productId)
    }

    private fun getNearStores() = viewModelScope.launch {
        userRepository.getNearStores().collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
//                        isLoading = false,
                        isError = true,
                        errorMessage = result.message
                    )
                }
                is Result.Loading -> _state.update {
                    it.copy(
//                        isLoading = true,
                        isError = false,
                        errorMessage = null
                    )
                }
                is Result.Success -> _state.update {
                    it.copy(
//                        isLoading = false,
                        isError = true,
                        errorMessage = null,
                        nearStores = result.data
                    )
                }
            }
        }
    }

    private fun getPopularProducts() = viewModelScope.launch {
        userRepository.getPopularProducts().collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = result.message
                    )
                }
                is Result.Loading -> _state.update {
                    it.copy(
                        isLoading = true,
                        isError = false,
                        errorMessage = null
                    )
                }
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = null,
                        popularProducts = result.data
                    )
                }
            }
        }
    }
}