package com.artech.requestsappandroid.ui.screens.main.models

sealed class MainViewEvent {
    object SplashScreenEnter : MainViewEvent()
    object ExitApplication : MainViewEvent()
}