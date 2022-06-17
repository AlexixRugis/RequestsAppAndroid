package com.artech.requestsappandroid.presentation.ui.screens.account

import com.artech.requestsappandroid.domain.models.AccountData

data class AccountViewState(
    val accountData : AccountData? = null,
    val isLoading : Boolean = false,
    val error: String = ""
)