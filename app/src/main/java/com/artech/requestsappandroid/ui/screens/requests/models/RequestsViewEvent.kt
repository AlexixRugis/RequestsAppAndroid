package com.artech.requestsappandroid.ui.screens.requests.models

sealed class RequestsViewEvent {
    object EnterScreen: RequestsViewEvent()
    object ReloadRequests: RequestsViewEvent()
    data class ClickRequestItem(val id: Int) : RequestsViewEvent()
}