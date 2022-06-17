package com.artech.requestsappandroid.presentation.ui.select_parts

import com.artech.requestsappandroid.data.remote.dto.Part

data class SelectRepairPartsViewState(
    val parts : List<Part>? = null,
    val isLoading : Boolean = false,
    val error : String = ""
)