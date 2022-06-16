package com.artech.requestsappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.artech.requestsappandroid.ui.screens.main.MainScreen
import com.artech.requestsappandroid.ui.screens.main.MainViewModel
import com.artech.requestsappandroid.ui.theme.RequestsAppAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RequestsAppAndroidTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = hiltViewModel()
                mainViewModel.navController = navController
                MainScreen(navController = navController, viewModel = mainViewModel)
            }
        }
    }
}