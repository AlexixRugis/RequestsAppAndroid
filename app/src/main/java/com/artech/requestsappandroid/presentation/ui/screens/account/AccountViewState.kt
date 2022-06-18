package com.artech.requestsappandroid.presentation.ui.screens.account

import com.artech.requestsappandroid.data.remote.dto.Employee
import com.artech.requestsappandroid.data.remote.dto.RepairTasks

data class AccountViewState(
    val accountData : Employee? = null,
    val isLoadingAccountData : Boolean = false,
    val errorAccountData: String = "",
    val tasks : RepairTasks? = null,
    val isLoadingTasks : Boolean = false,
    val errorTasks: String = ""
)