package com.msomu.androidkt.model

import kotlinx.serialization.Serializable

@Serializable
data class Prediction(
    val bytesBase64Encoded: String,
    val mimeType: String
)