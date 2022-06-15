package com.artech.requestsappandroid.data.remote.models

data class RepairTask(
    val executor: Employee,
    val id: Int,
    val is_completed: Boolean,
    val parts: List<PartRequest>,
    val repairRequest: RepairRequest,
    val status_photo: Any
)