package com.artech.requestsappandroid.presentation.ui.screens.change_password

data class ChangePasswordViewState(
    val password: String = "",
    val passwordConfirm: String = "",
    val passwordError: String = "",
    val isChanged: Boolean = false
)