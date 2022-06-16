package com.artech.requestsappandroid.ui.screens.task_details.models

import com.artech.requestsappandroid.data.remote.models.RepairTask

data class TaskDetailsState(
    val info: RepairTask? = null,
    val isLoading: Boolean = true
)