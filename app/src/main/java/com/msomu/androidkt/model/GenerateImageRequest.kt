package com.msomu.androidkt.model

import kotlinx.serialization.Serializable

@Serializable
data class GenerateImageRequest(
    val instances: List<Instance>,
    val parameters: Parameters
)