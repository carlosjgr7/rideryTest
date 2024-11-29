package com.carlosjgr7.riderytest.main.data.datasource.network

import com.carlosjgr7.riderytest.main.data.datasource.network.request.ApiRequest
import com.carlosjgr7.riderytest.main.data.datasource.network.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface IMainServices {
     @GET("v2/venues/search")
        suspend fun getVenues(@QueryMap request: Map<String, String>): ApiResponse
}