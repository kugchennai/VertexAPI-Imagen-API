package com.msomu.androidkt.presentation.ui.screen

import android.graphics.Bitmap

sealed interface ImageUiState {

    /**
     * Empty state when the screen is first shown
     */
    data object Initial : ImageUiState

    /**
     * Still loading
     */
    data object Loading : ImageUiState

    /**
     * Text has been generated
     */
    data class Success(val bitmap: Bitmap) : ImageUiState

    /**
     * There was an error generating text
     */
    data class Error(
        val errorMessage: String
    ) : ImageUiState
}
