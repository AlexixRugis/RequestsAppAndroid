package com.artech.requestsappandroid.ui.screens.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artech.requestsappandroid.ui.request_details.RequestDetailsScreen
import com.artech.requestsappandroid.ui.request_details.RequestDetailsViewModel
import com.artech.requestsappandroid.ui.screens.account.AccountScreen
import com.artech.requestsappandroid.ui.screens.account.AccountViewModel
import com.artech.requestsappandroid.ui.screens.login.LoginScreen
import com.artech.requestsappandroid.ui.screens.login.LoginViewModel
import com.artech.requestsappandroid.ui.screens.main.models.LoadingState
import com.artech.requestsappandroid.ui.screens.main.models.MainViewEvent
import com.artech.requestsappandroid.ui.screens.requests.RequestsScreen
import com.artech.requestsappandroid.ui.screens.requests.RequestsViewModel
import com.artech.requestsappandroid.ui.screens.task_details.TaskDetailsScreen
import com.artech.requestsappandroid.ui.screens.task_details.TaskDetailsViewModel
import com.artech.requestsappandroid.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class Screens(val route: String) {
    object Settings: Screens(Constants.Screens.SETTINGS_SCREEN)
    object Account: Screens(Constants.Screens.ACCOUNT_SCREEN)
    object Requests: Screens(Constants.Screens.REQUESTS_SCREEN)
    object RequestDetails: Screens(Constants.Screens.REQUEST_DETAILS_SCREEN)
    object TaskDetails: Screens(Constants.Screens.TASK_DETAILS_SCREEN)
}

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        delay(2000)
        viewModel.obtainEvent(MainViewEvent.SplashScreenEnter)
    }

    when (state.value.state) {
        LoadingState.LOADING -> SplashScreen()
        LoadingState.ACCOUNT -> MainView(navController = navController, viewModel = viewModel)
        LoadingState.LOGIN -> LoginView(viewModel = viewModel)
    }
}

@Composable
fun LoginView(viewModel: MainViewModel) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    loginViewModel.mainViewModel = viewModel
    loginViewModel.context = LocalContext.current
    LoginScreen(loginViewModel)
}

@Composable
fun MainView(navController: NavHostController, viewModel: MainViewModel) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                IconButton(onClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {Icon(Icons.Filled.Menu, contentDescription = "Меню") }
                Text("ARTech", fontSize = 20.sp)
            }
        },
        drawerContent={
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.obtainEvent(MainViewEvent.EnterRequestsScreen)
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
            ) {
                Text(
                    text = "Найти заявки",
                    modifier = Modifier.padding(10.dp)
                )
            }
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.obtainEvent(MainViewEvent.EnterSettingsScreen)
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
            ) {
                Text(
                    text = "Настройки",
                    modifier = Modifier.padding(10.dp)
                )
            }
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.obtainEvent(MainViewEvent.ExitApplication)
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
            ) {
                Text(
                    text = "Выход",
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screens.Account.route
            ) {
                composable(Screens.Account.route) {
                    val accountViewModel: AccountViewModel = hiltViewModel()
                    accountViewModel.navController = navController
                    AccountScreen(accountViewModel)
                }
                composable(Screens.Requests.route) {
                    val requestsViewModel: RequestsViewModel = hiltViewModel()
                    requestsViewModel.navController = navController
                    RequestsScreen(viewModel = requestsViewModel)
                }
                composable(Screens.RequestDetails.route + "/{id}") {
                    val requestDetailsViewModel: RequestDetailsViewModel = hiltViewModel()
                    requestDetailsViewModel.navController = navController
                    requestDetailsViewModel.id = it.arguments?.getString("id")?.toInt() ?: 1
                    RequestDetailsScreen(requestDetailsViewModel)
                }
                composable(Screens.TaskDetails.route + "/{id}") {
                    val taskDetailsViewModel: TaskDetailsViewModel = hiltViewModel()
                    taskDetailsViewModel.id = it.arguments?.getString("id")?.toInt() ?: 1
                    taskDetailsViewModel.navController = navController
                    TaskDetailsScreen(viewModel = taskDetailsViewModel)
                }
                composable(Screens.Settings.route) {

                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val isAnimating = remember { mutableStateOf(false) }
        val alphaAnimation = animateFloatAsState(
            targetValue = if (isAnimating.value) 1f else 0f,
            animationSpec = tween(durationMillis = 1500)
        )
        LaunchedEffect(key1 = true, block = { isAnimating.value = true })

        Icon(
            modifier = Modifier
                .size(120.dp)
                .scale(alphaAnimation.value)
                .alpha(alphaAnimation.value),
            imageVector = Icons.Default.Person,
            contentDescription = "",
            tint = Color.Black
        )
    }
}