package com.artech.requestsappandroid.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.models.AuthenticationStatus
import com.artech.requestsappandroid.ui.screens.main.models.AuthenticationState
import com.artech.requestsappandroid.ui.screens.main.models.MainViewEvent
import com.artech.requestsappandroid.ui.screens.main.models.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ApiRepository,
) : ViewModel(), EventHandler<MainViewEvent>{
    lateinit var navController: NavController

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state

    override fun obtainEvent(event: MainViewEvent) {
        when (event) {
            MainViewEvent.SplashScreenEnter -> enterScreenInvoked()
            MainViewEvent.ExitApplication -> exit()
        }
    }

    private fun exit() {
        viewModelScope.launch {
            val response = repository.logout()
            if (response.isSuccessful) {
                navController.backQueue.clear()
                navController.navigate(Screens.Login.route)
            }
        }
    }

    private fun enterScreenInvoked() {
        viewModelScope.launch {
            val status = getAuthenticationStatus()

            if (status != null) {
                if (status.is_authenticated) {
                    _state.value = _state.value.copy(state = AuthenticationState.SUCCESS)
                    delay(2000)
                    _state.value = _state.value.copy(loadTo = Screens.Account.route)
                } else {
                    _state.value = _state.value.copy(state = AuthenticationState.FAILURE)
                    delay(2000)
                    _state.value = _state.value.copy(loadTo = Screens.Login.route)
                }
            } else {
                _state.value = _state.value.copy(state = AuthenticationState.LOADING_ERROR)
            }
        }
    }

    private suspend fun getAuthenticationStatus(): AuthenticationStatus? {
        val isAuthenticated = repository.isAuthenticated()
        if (isAuthenticated.isSuccessful) {
            return isAuthenticated.body()
        } else {
            Log.e("TAG", "Failed to load authentication status ${isAuthenticated.errorBody()}")
            return null
        }
    }
}