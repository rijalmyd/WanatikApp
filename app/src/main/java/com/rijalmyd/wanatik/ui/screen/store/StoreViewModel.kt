package com.rijalmyd.wanatik.ui.screen.store

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
class StoreViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(StoreState())
    val state = _state.asStateFlow()

    fun onEvent(event: StoreEvent) {
        when (event) {
            is StoreEvent.OnGetProducts -> getProducts(event.storeId)
        }
    }

    private fun getProducts(storeId: String) = viewModelScope.launch {
        userRepository.getProductsByStoreId(storeId).collect { result ->
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
}