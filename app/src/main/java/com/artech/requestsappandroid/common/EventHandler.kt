package com.artech.requestsappandroid.common

interface EventHandler<T> {
    fun obtainEvent(event: T)
}