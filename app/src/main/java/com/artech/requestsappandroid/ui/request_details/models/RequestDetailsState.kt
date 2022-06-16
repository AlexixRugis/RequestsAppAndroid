package com.artech.requestsappandroid.ui.request_details.models

import com.artech.requestsappandroid.data.remote.models.RepairRequest
import com.artech.requestsappandroid.data.remote.models.RepairTask

data class RequestDetailsState(
    val info: RepairRequest? = null,
    val isLoading: Boolean = true
)