package com.artech.requestsappandroid.data.remote.models

data class PartRequest(
    val amount: Int,
    val id: Int,
    val is_completed: Boolean,
    val part: Part,
    val task: Int
)