package com.artech.requestsappandroid.domain.models

import com.artech.requestsappandroid.data.remote.dto.Employee
import com.artech.requestsappandroid.data.remote.dto.RepairTasks

data class AccountData(
    val employee: Employee,
    val tasks: RepairTasks
)