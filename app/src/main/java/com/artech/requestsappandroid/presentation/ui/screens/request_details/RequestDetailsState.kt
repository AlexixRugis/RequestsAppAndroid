package com.artech.requestsappandroid.presentation.ui.screens.request_details

import androidx.compose.runtime.Stable
import com.artech.requestsappandroid.data.remote.dto.RepairRequest

@Stable
data class RequestDetailsState(
    val request: RepairRequest? = null,
    val error: String = "",
    val isLoading: Boolean = false,
    val isAccepted: Boolean = false
)