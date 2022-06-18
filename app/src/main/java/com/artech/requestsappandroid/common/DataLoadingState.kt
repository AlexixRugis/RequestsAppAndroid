package com.artech.requestsappandroid.common

data class DataLoadingState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)