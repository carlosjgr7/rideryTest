package com.carlosjgr7.riderytest.main.domain

import com.carlosjgr7.riderytest.main.data.datasource.network.request.ApiRequest
import com.carlosjgr7.riderytest.main.data.repository.IMainRepository
import com.carlosjgr7.riderytest.main.domain.presentationdata.VenuesPrensentationData
import com.carlosjgr7.riderytest.main.domain.presentationdata.toPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetVenuesUseCase @Inject constructor(
    private val repository: IMainRepository
) {
    suspend operator fun invoke(data: ApiRequest): Flow<Result<List<VenuesPrensentationData>>> =
        repository.getVenues(data).map { result ->
            result.mapCatching { successResponse ->
                successResponse.response?.venues?.mapNotNull { it?.toPresentation() } ?: emptyList()
            }.fold(
                onSuccess = { presentationData ->
                    Result.success(presentationData)
                },
                onFailure = {
                    Result.failure(it)
                }
            )
        }
}

