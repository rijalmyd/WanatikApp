package com.rijalmyd.wanatik.ui.screen.scan

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijalmyd.wanatik.data.repository.UserRepository
import com.rijalmyd.wanatik.data.source.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ScanState())
    val state = _state.asStateFlow()
    private var classifyJob: Job? = null

    fun onEvent(event: ScanEvent) {
        when (event) {
            is ScanEvent.OnScanUpload -> classify(event.photoFile, event.photoUri)
            is ScanEvent.ResetState -> _state.update {
                ScanState()
            }
            is ScanEvent.OnSaveImageUri -> _state.update {
                it.copy(
                    imageUri = event.imageUri
                )
            }
            is ScanEvent.OnCancelClassify -> {
                classifyJob?.cancel()
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun classify(photoFile: File, photoUri: Uri) {
        classifyJob = viewModelScope.launch {
            userRepository.classify(photoFile, photoUri).collect { result ->
                when (result) {
                    is Result.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            loadingMessage = null,
                            loadingProgress = null,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                    is Result.Loading -> _state.update {
                        it.copy(
                            isLoading = true,
                            isError = false,
                            errorMessage = null,
                            loadingMessage = result.message,
                            loadingProgress = result.title
                        )
                    }
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                loadingMessage = null,
                                isError = true,
                                errorMessage = null,
                                loadingProgress = null,
                                classifyResult = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        classifyJob = null
    }
}