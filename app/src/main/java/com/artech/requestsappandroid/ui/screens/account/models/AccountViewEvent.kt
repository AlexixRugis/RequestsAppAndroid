package com.artech.requestsappandroid.ui.screens.account.models

sealed class AccountViewEvent {
    object EnterScreen : AccountViewEvent()
    object RefreshTasksData: AccountViewEvent()
    data class RepairRequestClicked(val id: Int): AccountViewEvent()
}