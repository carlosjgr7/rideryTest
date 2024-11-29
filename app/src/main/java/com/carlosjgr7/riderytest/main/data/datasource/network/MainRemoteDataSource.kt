package com.carlosjgr7.riderytest.main.data.datasource.network

import android.util.Log
import com.carlosjgr7.riderytest.main.data.datasource.network.request.ApiRequest
import com.carlosjgr7.riderytest.main.data.datasource.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainRemoteDataSource @Inject constructor(
    private val apiService: IMainServices
) {
    suspend fun getVenues(data:ApiRequest): Flow<Result<ApiResponse>> = flow {
        try {
            val response = apiService.getVenues(
                mapOf(
                    "v" to (data.version ?: "20231010"),
                    "ll" to data.location,
                    "query" to (data.searched ?: "supermercado"),
                    "radius" to (data.radius?.toString() ?: "20000"),
                    "oauth_token" to (data.oauthToken?.toString()?:"")

                )
            )
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}