package com.carlosjgr7.riderytest.login.data.datasource.network

import android.util.Log
import com.carlosjgr7.riderytest.login.data.datasource.local.response.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
) {
    suspend fun iniciarSesionConGoogle() =
        Result.success(
            User(
                displayName = "Carlos",
                email = "william.henry.moody@my-own-personal-domain.com",
            )
        )


}