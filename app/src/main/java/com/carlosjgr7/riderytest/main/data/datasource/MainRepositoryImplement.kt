package com.carlosjgr7.riderytest.main.data.datasource

import com.carlosjgr7.riderytest.main.data.datasource.network.MainRemoteDataSource
import com.carlosjgr7.riderytest.main.data.datasource.network.request.ApiRequest
import com.carlosjgr7.riderytest.main.data.datasource.network.response.ApiResponse
import com.carlosjgr7.riderytest.main.data.repository.IMainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImplement
@Inject constructor(
    private val repository: MainRemoteDataSource
) : IMainRepository {
    override suspend fun getVenues(data: ApiRequest): Flow<Result<ApiResponse>> =
        repository.getVenues(data)

}
