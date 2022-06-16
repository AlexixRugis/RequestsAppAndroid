package com.artech.requestsappandroid.ui.screens.account.models

import com.artech.requestsappandroid.data.remote.models.Employee
import com.artech.requestsappandroid.data.remote.models.Post

data class AccountViewState(
    val info: Employee = Employee("", "", "", Post(1, "")),
    val isLoading: Boolean = true
)