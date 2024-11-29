package com.carlosjgr7.riderytest.main.data.datasource.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("categoryCode")
    val categoryCode: Int?,
    @SerialName("icon")
    val icon: Icon?,
    @SerialName("id")
    val id: String?,
    @SerialName("mapIcon")
    val mapIcon: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("pluralName")
    val pluralName: String?,
    @SerialName("primary")
    val primary: Boolean?,
    @SerialName("shortName")
    val shortName: String?
)