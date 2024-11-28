package com.carlosjgr7.riderytest.login.domain

import com.carlosjgr7.riderytest.login.data.datasource.local.response.User
import com.carlosjgr7.riderytest.login.data.repository.ILoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val authenticationRepository: ILoginRepository
) {
    operator fun invoke(account: GoogleSignInAccount): Flow<Result<User>> = flow {
        try {
            authenticationRepository.loginWithGoogle(account)
            emit(Result.success(User(
                account.displayName ?: "",
                account.email ?: "",
                account.photoUrl.toString()
            )))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

