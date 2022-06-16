package com.artech.requestsappandroid.ui.screens.main.models

import androidx.compose.runtime.Stable

enum class LoadingState {
    LOADING,
    LOGIN,
    ACCOUNT
}

@Stable
data class MainViewState(
    val state: LoadingState = LoadingState.LOADING,
)