package com.msomu.androidkt.presentation.viewmodel

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msomu.androidkt.network.ApiState
import com.msomu.androidkt.presentation.ui.screen.ImageUiState
import com.msomu.androidkt.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: Repository) : ViewModel(){
    private val _uiState: MutableStateFlow<ImageUiState> = MutableStateFlow(ImageUiState.Initial)
    val uiState: StateFlow<ImageUiState> = _uiState.asStateFlow()

    fun generateImage(userInput: String) {
        viewModelScope.launch {
            try {
                val generateImageResponse = repository.generateImage(userInput)
                if (generateImageResponse is ApiState.Success) {
                    val base64ImageString = generateImageResponse.data.predictions[0].bytesBase64Encoded
                    // Convert base64 to Bitmap
                    val imageBytes = Base64.decode(base64ImageString, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    _uiState.value = ImageUiState.Success(bitmap)
                } else {
                    Log.e("msomu", (generateImageResponse as ApiState.Error).errorMsg)
                    _uiState.value = ImageUiState.Error("Failed to generate image ${(generateImageResponse as ApiState.Error).errorMsg}")
                }
            } catch (e: Exception) {
                _uiState.value = ImageUiState.Error(e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }
}