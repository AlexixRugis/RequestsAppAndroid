package com.artech.requestsappandroid.common

// Состояние асинхронной загрузки данных

data class DataLoadingState<T>(
    // Данные
    val data: T? = null,
    // Статус загрузки
    val isLoading: Boolean = false,
    // Ошибка
    val error: String = ""
)