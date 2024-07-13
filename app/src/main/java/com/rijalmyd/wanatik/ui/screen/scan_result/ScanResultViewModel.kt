package com.rijalmyd.wanatik.ui.screen.scan_result

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
class ScanResultViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ScanResultState())
    val state = _state.asStateFlow()

    fun onEvent(event: ScanResultEvent) {
        when (event) {
            is ScanResultEvent.OnGetProducts -> getProducts(event.classDetected)
        }
    }

    private fun getProducts(classDetected: String) = viewModelScope.launch {
        userRepository.getRelatedProducts(classDetected).collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                is Result.Loading -> _state.update {
                    it.copy(
                        isLoading = true,
                    )
                }
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        products = result.data
                    )
                }
            }
        }
    }
}