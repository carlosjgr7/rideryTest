package com.carlosjgr7.riderytest.main.data.datasource.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("unreadCount")
    val unreadCount: Int?
)