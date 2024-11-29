package com.carlosjgr7.riderytest.main.data.repository

import com.carlosjgr7.riderytest.main.data.datasource.network.request.ApiRequest
import com.carlosjgr7.riderytest.main.data.datasource.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IMainRepository {

    suspend fun getVenues(data: ApiRequest): Flow<Result<ApiResponse>>

}