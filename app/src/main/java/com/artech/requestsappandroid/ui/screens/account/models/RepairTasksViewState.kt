package com.artech.requestsappandroid.ui.screens.account.models

import com.artech.requestsappandroid.data.remote.models.RepairTask

data class RepairTasksViewState(
    val tasks: List<RepairTask> = emptyList(),
    val isLoading: Boolean = true
)