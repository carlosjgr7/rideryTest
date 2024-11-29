package com.carlosjgr7.riderytest.main.data.datasource.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    @SerialName("v") val version: String? = "20231010",
    @SerialName("ll") val location: String,
    @SerialName("query") val searched: String? = "supermercado",
    @SerialName("radius") val radius: Int? = 2000,
    @SerialName("oauth_token") val oauthToken: String? = "NKZILEYOKQAM32VYARCXX5JRKFVUZHVG3RBUEJVQGDXUO2NC"

)

