package com.artech.requestsappandroid.ui.screens.requests.models

import com.artech.requestsappandroid.data.remote.models.RepairRequest

data class RequestsViewState(
    val requests: List<RepairRequest> = emptyList(),
    val isLoading: Boolean = true
)