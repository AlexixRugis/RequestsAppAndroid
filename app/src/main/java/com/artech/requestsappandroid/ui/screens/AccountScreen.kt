package com.artech.requestsappandroid.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.artech.requestsappandroid.MainViewModel
import com.artech.requestsappandroid.navigation.Screens
import com.artech.requestsappandroid.ui.viewmodels.AccountViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(navController: NavController, viewModel: AccountViewModel = hiltViewModel()) {
    val isExiting = remember {
        mutableStateOf(false)
    }
    val composableScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.isRefreshing),
            onRefresh = {
                viewModel.refresh()
            },
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    composableScope.launch {
                        isExiting.value = true
                        if (viewModel.logout()) {
                            navController.navigate(Screens.Login.route)
                        }
                        isExiting.value = false
                    }
                },
                enabled = !isExiting.value
                    ) {
                    Text(text = "Выход")
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(viewModel.mutableList) { i, item ->
                        Text("$i $item")
                    }
                }    
            }
            
        }
    }
}