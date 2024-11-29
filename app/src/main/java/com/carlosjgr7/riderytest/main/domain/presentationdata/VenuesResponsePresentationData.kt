package com.carlosjgr7.riderytest.main.domain.presentationdata

import com.carlosjgr7.riderytest.main.data.datasource.network.response.ApiResponse
import com.carlosjgr7.riderytest.main.data.datasource.network.response.Venue

data class VenuesPrensentationData(
    val name: String,
    val lat: Double,
    val lng: Double,
)



fun Venue.toPresentation(): VenuesPrensentationData {
    return VenuesPrensentationData(
        name = this.name ?: "",
        lat = this.location?.lat ?: 0.0,
        lng = this.location?.lng ?: 0.0

    )
}