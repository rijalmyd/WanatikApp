package com.rijalmyd.wanatik.ui.screen.list_all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijalmyd.wanatik.data.repository.UserRepository
import com.rijalmyd.wanatik.data.source.Result
import com.rijalmyd.wanatik.ui.screen.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAllViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ListAllState())
    val state = _state.asStateFlow()

    fun onEvent(event: ListAllEvent) {
        when (event) {
            is ListAllEvent.OnGetList -> getList(event.type)
        }
    }

    private fun getList(type: ListAllType) {
        when (type) {
            ListAllType.PRODUCT -> getPopularProducts()
            ListAllType.STORE -> getNearStores()
        }
    }

    private fun getNearStores() = viewModelScope.launch {
        userRepository.getNearStores().collect { result ->
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
                        stores = result.data
                    )
                }
            }
        }
    }

    private fun getPopularProducts() = viewModelScope.launch {
        userRepository.getAllProducts().collect { result ->
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
                        products = result.data
                    )
                }
            }
        }
    }
}