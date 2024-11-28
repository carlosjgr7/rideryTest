package com.carlosjgr7.riderytest.login.domain

import com.carlosjgr7.riderytest.login.data.datasource.local.response.User
import com.carlosjgr7.riderytest.login.data.repository.ILoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(
    private val authenticationRepository: ILoginRepository
) {
    suspend operator fun invoke() = authenticationRepository.checkLogin()
}

