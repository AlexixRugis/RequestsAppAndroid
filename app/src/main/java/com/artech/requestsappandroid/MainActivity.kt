package com.artech.requestsappandroid

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.artech.requestsappandroid.presentation.ui.screens.main.MainScreen
import com.artech.requestsappandroid.presentation.ui.screens.main.MainViewModel
import com.artech.requestsappandroid.presentation.ui.screens.main.models.MainViewEvent
import com.artech.requestsappandroid.presentation.ui.theme.RequestsAppAndroidTheme
import com.artech.requestsappandroid.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val mainViewModel: MainViewModel = hiltViewModel()
            mainViewModel.navController = navController
            mainViewModel.context = LocalContext.current

            val state = mainViewModel.state.collectAsState()

            LaunchedEffect(key1 = true) {
                mainViewModel.initialize()
            }

            RequestsAppAndroidTheme(state.value.darkTheme) {
                MainScreen(navController = navController, viewModel = mainViewModel)
            }
        }
    }
}