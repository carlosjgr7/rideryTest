package com.carlosjgr7.riderytest.login.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface ILoginRepository {

    suspend fun loginWithGoogle(account: GoogleSignInAccount)
    suspend fun logoutWithGoogle(): Boolean
    suspend fun checkLogin(): Boolean
}