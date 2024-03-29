package com.artech.requestsappandroid.presentation.ui.screens.main

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.AuthenticationStatus
import com.artech.requestsappandroid.presentation.ui.screens.main.models.LoadingState
import com.artech.requestsappandroid.presentation.ui.screens.main.models.MainViewEvent
import com.artech.requestsappandroid.presentation.ui.screens.main.models.MainViewState
import com.artech.requestsappandroid.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ApiRepository,
) : ViewModel(), EventHandler<MainViewEvent>{
    lateinit var navController: NavController
    lateinit var context: Context

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state

    fun initialize() {
        loadDarkThemeData()
    }

    override fun obtainEvent(event: MainViewEvent) {
        when (event) {
            MainViewEvent.SplashScreenEnter -> enterScreenInvoked()
            MainViewEvent.ExitApplication -> exit()
            MainViewEvent.ToggleDarkTheme -> toggleDarkTheme()
            MainViewEvent.LoginApplication -> navigateToAccountScreen()
            MainViewEvent.EnterRequestsScreen -> navigateToRequestsScreen()
            MainViewEvent.EnterSettingsScreen -> navigateToSettingsScreen()
        }
    }

    private fun navigateToSettingsScreen() {
        navController.navigate(Screens.Settings.route)
    }

    private fun navigateToRequestsScreen() {
        navController.navigate(Screens.Requests.route)
    }

    private fun navigateToAccountScreen() {
        _state.value = _state.value.copy(state = LoadingState.ACCOUNT)
    }

    private fun exit() {
        viewModelScope.launch {
            val response = repository.logout()
            if (response.isSuccessful) {
                _state.value = _state.value.copy(state = LoadingState.LOGIN)
            }
        }
    }

    private fun toggleDarkTheme() {
        val sharedPreferences = context.getSharedPreferences(
            Constants.SharedPreferences.APP_NAME, Context.MODE_PRIVATE
        )
        _state.value = _state.value.copy(darkTheme = !_state.value.darkTheme)
        sharedPreferences.edit {
            putBoolean(Constants.SharedPreferences.DARK_THEME, _state.value.darkTheme)
            apply()
        }
    }

    private fun loadDarkThemeData() {
        val sharedPreferences = context.getSharedPreferences(
            Constants.SharedPreferences.APP_NAME, Context.MODE_PRIVATE
        )
        _state.value = _state.value.copy(darkTheme = sharedPreferences.getBoolean(Constants.SharedPreferences.DARK_THEME, false))
    }

    private fun enterScreenInvoked() {
        viewModelScope.launch {
            val status = getAuthenticationStatus()

            if (status != null) {
                if (status.is_authenticated) {
                    _state.value = _state.value.copy(state = LoadingState.ACCOUNT)
                } else {
                    _state.value = _state.value.copy(state = LoadingState.LOGIN)
                }
            }
        }
    }

    private suspend fun getAuthenticationStatus(): AuthenticationStatus? {
        val isAuthenticated = repository.isAuthenticated()
        return if (isAuthenticated.isSuccessful) {
            isAuthenticated.body()
        } else {
            Log.e("TAG", "Failed to load authentication status ${isAuthenticated.errorBody()}")
            null
        }
    }
}