package com.carlosjgr7.riderytest.login.data.datasource

import android.util.Log
import com.carlosjgr7.riderytest.login.data.datasource.local.LoginLocalDataSource
import com.carlosjgr7.riderytest.login.data.datasource.local.response.User
import com.carlosjgr7.riderytest.login.data.repository.ILoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImplement
@Inject constructor(
    private val loginLocalDataSource: LoginLocalDataSource
) : ILoginRepository {
    override suspend fun loginWithGoogle(account: GoogleSignInAccount){
        loginLocalDataSource.saveGoogleSession(account)
    }

    override suspend fun logoutWithGoogle() = loginLocalDataSource.logoutGoogleSession()


    override suspend fun checkLogin(): Boolean = loginLocalDataSource.getGoogleSession()

}
