package com.artech.requestsappandroid.presentation.ui.screens.add_part

import com.artech.requestsappandroid.data.remote.dto.Part

data class AddPartViewState(
    val part : Part? = null,
    val isLoading : Boolean = false,
    val error : String = "",
    val amount : Int = 1,
    val isAdded : Boolean = false
)
