package com.msomu.androidkt.repository

import com.msomu.androidkt.model.GenerateImageRequest
import com.msomu.androidkt.model.GenerateImageResponse
import com.msomu.androidkt.model.Instance
import com.msomu.androidkt.model.Parameters
import com.msomu.androidkt.model.TodoItem
import com.msomu.androidkt.network.ApiService
import com.msomu.androidkt.network.ApiState
import com.msomu.androidkt.network.VertexApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val vertexApiService: VertexApiService,
) : Repository {

    override suspend fun getAllTodos(): ApiState<List<TodoItem>> = try {
        ApiState.Success(apiService.getAllTodoItems())
    } catch (e: Exception) {
        ApiState.Error(errorMsg = e.message.toString())
    }

    override suspend fun getTodoDetails(todoItemId: Int): ApiState<TodoItem> = try {
        ApiState.Success(apiService.getTodoItem(todoItemId))
    } catch (e: Exception) {
        ApiState.Error(errorMsg = e.message.toString())
    }

    override suspend fun generateImage(prompt: String): ApiState<GenerateImageResponse> {
        val request = GenerateImageRequest(
            instances = listOf(
                Instance(prompt)
            ), parameters = Parameters(1)
        )
        return try {
            ApiState.Success(vertexApiService
                .generateImage("Bearer ${getToken()}", request))
        } catch (e: Exception) {
            ApiState.Error(errorMsg = e.message.toString())
        }
    }

    private fun getToken(): String {
        // Implement your token retrieval logic here
        return ""
    }
}