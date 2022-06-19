package com.artech.requestsappandroid.presentation.ui.screens.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artech.requestsappandroid.presentation.ui.screens.account.AccountScreen
import com.artech.requestsappandroid.presentation.ui.screens.add_part.AddPartScreen
import com.artech.requestsappandroid.presentation.ui.screens.change_password.ChangePasswordScreen
import com.artech.requestsappandroid.presentation.ui.screens.complete_task.CompleteTaskScreen
import com.artech.requestsappandroid.presentation.ui.screens.login.LoginScreen
import com.artech.requestsappandroid.presentation.ui.screens.login.LoginViewModel
import com.artech.requestsappandroid.presentation.ui.screens.main.models.LoadingState
import com.artech.requestsappandroid.presentation.ui.screens.main.models.MainViewEvent
import com.artech.requestsappandroid.presentation.ui.screens.request_details.RequestDetailsScreen
import com.artech.requestsappandroid.presentation.ui.screens.requests.RequestsScreen
import com.artech.requestsappandroid.presentation.ui.screens.select_parts.SelectPartsScreen
import com.artech.requestsappandroid.presentation.ui.screens.settings.SettingsScreen
import com.artech.requestsappandroid.presentation.ui.screens.task_details.TaskDetailsScreen
import com.artech.requestsappandroid.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class Screens(val route: String) {
    object Settings: Screens(Constants.Screens.SETTINGS_SCREEN)
    object Account: Screens(Constants.Screens.ACCOUNT_SCREEN)
    object Requests: Screens(Constants.Screens.REQUESTS_SCREEN)
    object RequestDetails: Screens(Constants.Screens.REQUEST_DETAILS_SCREEN)
    object TaskDetails: Screens(Constants.Screens.TASK_DETAILS_SCREEN)
    object TaskRepairParts: Screens(Constants.Screens.TASK_REPAIR_PARTS)
    object ChangePassword: Screens(Constants.Screens.CHANGE_PASSWORD)
    object CompleteTask: Screens(Constants.Screens.COMPLETE_TASK)
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
                Text("Журнал заявок", fontSize = 20.sp)
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
                    AccountScreen(navController)
                }
                composable(Screens.Requests.route) {
                    RequestsScreen(navController)
                }
                composable(Screens.RequestDetails.route + "/{requestId}") {
                    RequestDetailsScreen(navController)
                }
                composable(Screens.TaskDetails.route + "/{taskId}") {
                    TaskDetailsScreen(navController, it.arguments!!.getString("taskId")!!.toInt())
                }
                composable(Screens.CompleteTask.route + "/{taskId}") {
                    CompleteTaskScreen(navController = navController)
                }
                composable(Screens.TaskRepairParts.route + "/{taskId}") {
                    SelectPartsScreen(navController, it.arguments!!.getString("taskId")!!.toInt())
                }
                composable(Screens.TaskRepairParts.route + "/{taskId}/{partId}") {
                    AddPartScreen(navController)
                }
                composable(Screens.Settings.route) {
                    SettingsScreen(navController = navController, viewModel = viewModel)
                }
                composable(Screens.ChangePassword.route) {
                    ChangePasswordScreen(navController)
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        val isAnimating = remember { mutableStateOf(false) }
        val alphaAnimation = animateFloatAsState(
            targetValue = if (isAnimating.value) 1f else 0f,
            animationSpec = tween(durationMillis = 1500)
        )
        LaunchedEffect(key1 = true, block = { isAnimating.value = true })

        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(100.dp).fillMaxWidth().align(Alignment.CenterHorizontally)

                        .scale(alphaAnimation.value)
                        .alpha(alphaAnimation.value),
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Журнал заявок",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}