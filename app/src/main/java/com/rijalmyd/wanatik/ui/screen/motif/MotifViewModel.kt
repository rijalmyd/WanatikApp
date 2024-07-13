package com.rijalmyd.wanatik.ui.screen.motif

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
class MotifViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MotifState())
    val state = _state.asStateFlow()

    fun onEvent(event: MotifEvent) {
        when (event) {
            is MotifEvent.OnGetMotifDetail -> {
                getDetailMotif(event.id)
                getProducts(event.id)
            }
        }
    }

    private fun getProducts(motifId: String) = viewModelScope.launch {
        userRepository.getRelatedProducts(motifId).collect { result ->
            when (result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> _state.update {
                    it.copy(
                        products = result.data
                    )
                }
            }
        }
    }

    private fun getDetailMotif(motifId: String) = viewModelScope.launch {
        userRepository.getMotifById(motifId).collect { result ->
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
                        motif = result.data
                    )
                }
            }
        }
    }
}