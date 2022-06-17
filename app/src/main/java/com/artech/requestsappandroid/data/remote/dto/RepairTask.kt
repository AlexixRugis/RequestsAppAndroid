package com.artech.requestsappandroid.data.remote.dto

data class RepairTask(
    val executor: Employee,
    val id: Int,
    val is_completed: Boolean,
    val parts: List<PartRequest>,
    val request: RepairRequest,
    val status_photo: Any
)