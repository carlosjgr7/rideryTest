package com.carlosjgr7.riderytest.main.data.datasource.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("address")
    val address: String? = "",
    @SerialName("cc")
    val cc: String? = "",
    @SerialName("city")
    val city: String? = "",
    @SerialName("country")
    val country: String? = "",
    @SerialName("crossStreet")
    val crossStreet: String? = "",
    @SerialName("distance")
    val distance: Int?,
    @SerialName("formattedAddress")
    val formattedAddress: List<String?>?,
    @SerialName("labeledLatLngs")
    val labeledLatLngs: List<LabeledLatLng?>?,
    @SerialName("lat")
    val lat: Double?,
    @SerialName("lng")
    val lng: Double?,
    @SerialName("neighborhood")
    val neighborhood: String? = "",
    @SerialName("postalCode")
    val postalCode: String? = "",
    @SerialName("state")
    val state: String? = ""
)