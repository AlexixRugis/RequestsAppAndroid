package com.artech.requestsappandroid.data.remote.dto

data class PartRequest(
    val amount: Int,
    val id: Int,
    val is_completed: Boolean,
    val part: RepairPart,
    val task: Int
)