package com.artech.requestsappandroid.ui.screens.login.models

sealed class LoginEvent {
    object Login : LoginEvent()
    object ChangePasswordVisibility : LoginEvent()
    data class EmailChanged(val value: String) : LoginEvent()
    data class PasswordChanged(val value: String) : LoginEvent()
}