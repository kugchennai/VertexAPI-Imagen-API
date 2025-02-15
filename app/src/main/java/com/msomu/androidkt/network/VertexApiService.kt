package com.msomu.androidkt.network

import com.msomu.androidkt.model.GenerateImageRequest
import com.msomu.androidkt.model.GenerateImageResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface VertexApiService {
    @POST("v1/projects/{{project-name}}/locations/us-central1/publishers/google/models/imagen-3.0-fast-generate-001:predict")
    suspend fun generateImage(
        @Header("Authorization") authorization: String,
        @Body request: GenerateImageRequest,
    ): GenerateImageResponse
}