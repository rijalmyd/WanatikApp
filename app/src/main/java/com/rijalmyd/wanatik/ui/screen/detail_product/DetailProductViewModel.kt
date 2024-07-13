package com.rijalmyd.wanatik.ui.screen.detail_product

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
class DetailProductViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailProductState())
    val state = _state.asStateFlow()

    fun onEvent(event: DetailProductEvent) {
        when (event) {
            is DetailProductEvent.OnGetDetailProduct -> getDetailProduct(event.id)
        }
    }

    private fun getDetailProduct(id: String) = viewModelScope.launch {
        userRepository.getDetailProduct(id).collect { result ->
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
                        isLoading = true,
                        isError = false,
                        errorMessage = null,
                        product = result.data
                    )
                }
            }
        }
    }
}