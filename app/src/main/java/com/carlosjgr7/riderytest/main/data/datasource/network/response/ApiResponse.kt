package com.carlosjgr7.riderytest.main.data.datasource.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    @SerialName("meta")
    val meta: Meta?,
    @SerialName("notifications")
    val notifications: List<Notification?>?,
    @SerialName("response")
    val response: Response?
)