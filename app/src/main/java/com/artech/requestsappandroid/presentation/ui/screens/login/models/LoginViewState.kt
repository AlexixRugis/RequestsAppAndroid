package com.artech.requestsappandroid.presentation.ui.screens.login.models

data class LoginViewState(
    val email: String = "",
    val emailErrorState: Boolean = false,
    val password: String = "",
    val passwordErrorState: Boolean = false,
    val isPasswordHidden: Boolean = true,
    val isFormActive: Boolean = true
)
