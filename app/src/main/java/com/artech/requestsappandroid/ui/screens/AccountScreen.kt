package com.artech.requestsappandroid.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.artech.requestsappandroid.MainViewModel

@Composable
fun AccountScreen(navController: NavController, viewModel: MainViewModel) {
    /*val requests = viewModel.requests.observeAsState(listOf()).value
    requests.forEach {
        Log.e("TAG", it.toString())
    }*/

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "account", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
    }
}