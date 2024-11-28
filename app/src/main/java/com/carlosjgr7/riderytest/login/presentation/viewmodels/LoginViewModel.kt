package com.carlosjgr7.riderytest.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosjgr7.riderytest.core.Resources
import com.carlosjgr7.riderytest.core.di.dispachers.IoDispatcher
import com.carlosjgr7.riderytest.login.data.datasource.local.response.User
import com.carlosjgr7.riderytest.login.domain.CheckLoginUseCase
import com.carlosjgr7.riderytest.login.domain.LoginWithGoogleUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject internal constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val checkLoginUseCase: CheckLoginUseCase,
) : ViewModel() {
    private val _login =
        MutableStateFlow<Resources<User>>(Resources.Loading(false))
    val loginState: StateFlow<Resources<User>> get() = _login.asStateFlow()

    private val _checkLogin =
        MutableStateFlow(false)
    val checkLogin get() = _checkLogin.asStateFlow()




    fun loginWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch(dispatcher) {
            _login.value = Resources.Loading(true)

            loginWithGoogleUseCase.invoke(account).collect {
                it.fold(
                    onSuccess = {
                        _login.value = Resources.Success(it)
                    },
                    onFailure = {
                        _login.value = Resources.Failure(it)
                    }
                )
            }

        }
    }

    fun checkInfo(){
        viewModelScope.launch(dispatcher) {
           _checkLogin.value = checkLoginUseCase.invoke()
        }
    }


    fun configureGoogleSignIn() =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
}
