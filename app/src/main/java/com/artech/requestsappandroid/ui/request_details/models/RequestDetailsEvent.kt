package com.artech.requestsappandroid.ui.request_details.models

sealed class RequestDetailsEvent {
    object EnterScreen: RequestDetailsEvent()
    object AcceptRequest: RequestDetailsEvent()
}