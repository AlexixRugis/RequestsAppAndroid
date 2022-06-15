package com.artech.requestsappandroid.data.remote.models

data class RepairRequest(
    val description: String,
    val desired_date: String,
    val id: Int,
    val name: String,
    val org_address: String,
    val org_name: String,
    val org_phone: String
)