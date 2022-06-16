package com.artech.requestsappandroid.ui.screens.task_details.models

import com.artech.requestsappandroid.data.remote.models.Part
import com.artech.requestsappandroid.data.remote.models.RepairTask

enum class TaskDetailSubState {
    INFO,
    SELECT_PARTS,
    ADD_PART
}

data class TaskDetailsState(
    val subState: TaskDetailSubState = TaskDetailSubState.INFO,
    val info: RepairTask? = null,
    val repairParts: List<Part> = emptyList(),
    val selectedPart: Part? = null,
    val selectedPartAmount: Int = 1,
    val isLoading: Boolean = true
)