package com.artech.requestsappandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artech.requestsappandroid.MainViewModel
import com.artech.requestsappandroid.ui.screens.AccountScreen
import com.artech.requestsappandroid.ui.screens.LoginScreen
import com.artech.requestsappandroid.ui.screens.SplashScreen
import com.artech.requestsappandroid.utils.Constants

sealed class Screens(val route: String) {
    object Splash: Screens(Constants.Screens.SPLASH_SCREEN)
    object Login: Screens(Constants.Screens.LOGIN_SCREEN)
    object Account: Screens(Constants.Screens.ACCOUNT_SCREEN)
    object Requests: Screens(Constants.Screens.REQUESTS_SCREEN)
    object RequestDetails: Screens(Constants.Screens.REQUEST_DETAILS_SCREEN)
    object TaskDetails: Screens(Constants.Screens.TASK_DETAILS_SCREEN)
}

@Composable
fun SetupNavHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(Screens.Splash.route) {
            SplashScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screens.Login.route) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screens.Account.route) {
            AccountScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screens.Requests.route) {

        }
        composable(Screens.RequestDetails.route) {

        }
        composable(Screens.TaskDetails.route) {

        }
    }
}