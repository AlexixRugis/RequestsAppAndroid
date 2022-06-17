package com.artech.requestsappandroid.presentation.ui.screens.requests

import com.artech.requestsappandroid.data.remote.dto.RepairRequests

data class RequestsViewState(
    val requests: RepairRequests? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)