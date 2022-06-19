package com.artech.requestsappandroid.presentation.ui.screens.task_details.models

import com.artech.requestsappandroid.data.remote.dto.RepairTask

data class TaskDetailsState(
    val task: RepairTask? = null,
    val isLoading: Boolean = false,
    val error : String = "",
    val isCompleted : Boolean = false
)