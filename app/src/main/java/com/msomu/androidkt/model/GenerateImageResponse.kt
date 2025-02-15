package com.msomu.androidkt.model

import kotlinx.serialization.Serializable

@Serializable
data class GenerateImageResponse(
    val predictions: List<Prediction>
)