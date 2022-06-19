package com.artech.requestsappandroid.presentation.ui.screens.add_part

import com.artech.requestsappandroid.data.remote.dto.RepairPart

data class AddPartViewState(
    val repairPart : RepairPart? = null,
    val isLoading : Boolean = false,
    val error : String = "",
    val amount : Int = 1,
    val isAdded : Boolean = false
)
