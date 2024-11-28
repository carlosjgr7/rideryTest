package com.carlosjgr7.riderytest.main.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosjgr7.riderytest.core.Resources
import com.carlosjgr7.riderytest.core.di.dispachers.IoDispatcher
import com.carlosjgr7.riderytest.login.domain.LoginWithGoogleUseCase
import com.carlosjgr7.riderytest.main.domain.LogoutWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val logoutWithGoogleUseCase: LogoutWithGoogleUseCase,
) : ViewModel() {

    private val _sesion =
        MutableStateFlow(false)
    val sesion get() = _sesion.asStateFlow()

    fun logoutWithGoogle() {
        viewModelScope.launch(dispatcher) {
            logoutWithGoogleUseCase.invoke().collect { result ->
                result.fold(
                    onSuccess = {
                        _sesion.value = true
                    },
                    onFailure = {
                        _sesion.value = false
                    }
                )
            }
        }
    }

}


