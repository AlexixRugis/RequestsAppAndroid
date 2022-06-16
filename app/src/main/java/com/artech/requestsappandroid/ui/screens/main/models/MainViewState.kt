package com.artech.requestsappandroid.ui.screens.main.models

import androidx.compose.runtime.Stable

enum class AuthenticationState {
    LOADING,
    LOADING_ERROR,
    SUCCESS,
    FAILURE
}

@Stable
data class MainViewState(
    val state: AuthenticationState = AuthenticationState.LOADING,
    val loadTo: String? = null
)