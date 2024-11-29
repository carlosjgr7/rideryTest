package com.carlosjgr7.riderytest.main.data.datasource.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LabeledLatLng(
    @SerialName("label")
    val label: String?,
    @SerialName("lat")
    val lat: Double?,
    @SerialName("lng")
    val lng: Double?
)