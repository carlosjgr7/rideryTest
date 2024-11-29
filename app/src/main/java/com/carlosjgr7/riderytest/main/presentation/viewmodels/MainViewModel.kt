package com.carlosjgr7.riderytest.main.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosjgr7.riderytest.core.Resources
import com.carlosjgr7.riderytest.core.di.dispachers.IoDispatcher
import com.carlosjgr7.riderytest.main.data.datasource.network.request.ApiRequest
import com.carlosjgr7.riderytest.main.domain.GetVenuesUseCase
import com.carlosjgr7.riderytest.main.domain.LogoutWithGoogleUseCase
import com.carlosjgr7.riderytest.main.domain.presentationdata.VenuesPrensentationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val logoutWithGoogleUseCase: LogoutWithGoogleUseCase,
    private val getVenuesUseCase: GetVenuesUseCase
) : ViewModel() {

    private val _sesion =
        MutableStateFlow(false)
    val sesion get() = _sesion.asStateFlow()

    private val _venues =
        MutableStateFlow<Resources<List<VenuesPrensentationData>>>(Resources.Loading(false))
    val venues get() = _venues.asStateFlow()


     fun getVenues(location: String) {
        val data = ApiRequest(location = location)
        viewModelScope.launch(dispatcher) {
            getVenuesUseCase.invoke(data).collect { result ->
                result.fold(
                    onSuccess = {
                        _venues.value = Resources.Success(it)
                    },
                    onFailure = {
                        Log.i("getVenues",  "Error: ${it.message}")
                        _venues.value = Resources.Failure(it)
                    }
                )
            }
        }
    }


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


