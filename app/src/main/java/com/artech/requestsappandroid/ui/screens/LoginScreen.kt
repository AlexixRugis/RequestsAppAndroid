package com.artech.requestsappandroid.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.artech.requestsappandroid.MainViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Login")
    }
}