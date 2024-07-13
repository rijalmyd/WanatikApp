package com.rijalmyd.wanatik.ui.screen.search

import android.util.Log
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
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryTextChange -> {
                _state.update {
                    it.copy(
                        query = event.query
                    )
                }
            }
            is SearchEvent.OnSearch -> searchProducts(event.query)
        }
    }

    private fun searchProducts(query: String) = viewModelScope.launch {
        Log.d("INIQUEYYY", "searchProducts: $query")
        userRepository.searchProducts(query).collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    Log.d("INIQUEYYY", "searchProducts: $query, errr: ${result.message}")
                    it.copy(
                        isLoading = false,
                        errorMessage = result.message,
                    )
                }
                is Result.Loading -> _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                    )
                }
                is Result.Success -> _state.update {
                    Log.d("INIQUEYYY", "searchProducts: $query, wd : ${result.data}")
                    it.copy(
                        isLoading = false,
                        products = result.data,
                        errorMessage = null,
                    )
                }
            }
        }
    }
}