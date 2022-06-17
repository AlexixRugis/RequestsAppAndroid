package com.artech.requestsappandroid.presentation.ui.screens.main.models

sealed class MainViewEvent {
    object SplashScreenEnter : MainViewEvent()
    object ExitApplication : MainViewEvent()
    object LoginApplication : MainViewEvent()
    object EnterRequestsScreen : MainViewEvent()
    object EnterSettingsScreen : MainViewEvent()
}