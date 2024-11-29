package com.carlosjgr7.riderytest.main.data.datasource.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Venue(
    @SerialName("categories")
    val categories: List<Category?>?,
    @SerialName("createdAt")
    val createdAt: Int?,
    @SerialName("id")
    val id: String?,
    @SerialName("location")
    val location: Location?,
    @SerialName("name")
    val name: String?
)