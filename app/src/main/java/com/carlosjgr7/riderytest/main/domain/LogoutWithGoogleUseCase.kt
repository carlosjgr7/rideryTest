package com.carlosjgr7.riderytest.main.domain

import com.carlosjgr7.riderytest.login.data.datasource.local.response.User
import com.carlosjgr7.riderytest.login.data.repository.ILoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutWithGoogleUseCase @Inject constructor(
    private val authenticationRepository: ILoginRepository
) {
    operator fun invoke(): Flow<Result<Boolean>> = flow {
        try {
            authenticationRepository.logoutWithGoogle()
            emit(Result.success(true))
        }catch (e: Exception){
            emit(Result.failure(e))
        }
    }
}
