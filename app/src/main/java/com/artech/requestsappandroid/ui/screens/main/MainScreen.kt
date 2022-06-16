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
import com.artech.requestsappandroid.ui.screens.RequestDetailsScreen
import com.artech.requestsappandroid.ui.screens.account.AccountScreen
import com.artech.requestsappandroid.ui.screens.account.AccountViewModel
import com.artech.requestsappandroid.ui.screens.login.LoginScreen
import com.artech.requestsappandroid.ui.screens.login.LoginViewModel
import com.artech.requestsappandroid.ui.screens.main.models.AuthenticationState
import com.artech.requestsappandroid.ui.screens.main.models.MainViewEvent
import com.artech.requestsappandroid.ui.screens.task_details.TaskDetailsScreen
import com.artech.requestsappandroid.ui.screens.task_details.TaskDetailsViewModel
import com.artech.requestsappandroid.ui.viewmodels.RequestDetailViewModel
import com.artech.requestsappandroid.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class Screens(val route: String) {
    object Login: Screens(Constants.Screens.LOGIN_SCREEN)
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

    when (state.value.loadTo != null) {
        false -> SplashScreen(state = state.value.state)
        true -> MainView(navController = navController,
            startDestination = state.value.loadTo!!,
            viewModel = viewModel
        )
    }
}

@Composable
fun MainView(navController: NavHostController, startDestination: String, viewModel: MainViewModel) {
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
                    .padding(top=8.dp)
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
                startDestination = startDestination
            ) {
                composable(Screens.Login.route) {
                    val loginViewModel: LoginViewModel = hiltViewModel()
                    loginViewModel.navController = navController
                    loginViewModel.context = LocalContext.current
                    LoginScreen(loginViewModel)
                }
                composable(Screens.Account.route) {
                    val accountViewModel: AccountViewModel = hiltViewModel()
                    accountViewModel.navController = navController
                    AccountScreen(accountViewModel)
                }
                composable(Screens.Requests.route) {

                }
                composable(Screens.RequestDetails.route + "/{id}") {
                    val requestDetailsViewModel: RequestDetailViewModel = hiltViewModel()
                    requestDetailsViewModel.id = it.arguments?.getString("id")?.toInt() ?: 1
                    requestDetailsViewModel.refresh()
                    RequestDetailsScreen(requestDetailsViewModel)
                }
                composable(Screens.TaskDetails.route + "/{id}") {
                    val taskDetailsViewModel: TaskDetailsViewModel = hiltViewModel()
                    taskDetailsViewModel.id = it.arguments?.getString("id")?.toInt() ?: 1
                    taskDetailsViewModel.navController = navController
                    TaskDetailsScreen(viewModel = taskDetailsViewModel)
                }
            }
        }
    }
}

@Composable
fun SplashScreen(state: AuthenticationState) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            AuthenticationState.LOADING -> Splash()
            AuthenticationState.LOADING_ERROR -> LoadingError()
            AuthenticationState.SUCCESS -> Authenticated()
            AuthenticationState.FAILURE -> NotAuthenticated()
        }
    }
}

@Composable
fun Splash() {
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

@Composable
fun LoadingError() {
    val isAnimating = remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (isAnimating.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
    LaunchedEffect(key1 = true, block = { isAnimating.value = true })
    Text(
        text = "Возникла ошибка подключения",
        fontSize = 24.sp,
        modifier = Modifier
            .scale(alphaAnimation.value)
            .alpha(alphaAnimation.value),
    )
}

@Composable
fun Authenticated() {
    val isAnimating = remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (isAnimating.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
    LaunchedEffect(key1 = true, block = { isAnimating.value = true })
    Text(
        text = "С возвращением!",
        fontSize = 24.sp,
        modifier = Modifier
            .scale(alphaAnimation.value)
            .alpha(alphaAnimation.value),
    )
}

@Composable
fun NotAuthenticated() {
    val isAnimating = remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (isAnimating.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
    LaunchedEffect(key1 = true, block = { isAnimating.value = true })
    Text(
        text = "Добро пожаловать!",
        fontSize = 24.sp,
        modifier = Modifier
            .scale(alphaAnimation.value)
            .alpha(alphaAnimation.value),
    )
}