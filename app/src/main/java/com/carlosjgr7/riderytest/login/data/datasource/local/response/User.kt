package com.carlosjgr7.riderytest.login.data.datasource.local.response


data class User(
    val displayName: String,
    val email: String,
    val photoUrl: String? = null,
    val uid: String? = null
)