package com.artech.requestsappandroid.presentation.ui.screens.select_parts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.artech.requestsappandroid.presentation.ui.components.RepairPartView
import com.artech.requestsappandroid.presentation.ui.components.SearchView
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens

@Composable
fun SelectPartsScreen(navController: NavController, taskId: Int, viewModel: SelectPartsViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.initialize()
    }

    Surface(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Запчасти",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.SemiBold
            )
            SearchView(onClickSearch = { viewModel.updateParts(it) })
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(state.value.data?: emptyList()) { i, item ->
                    RepairPartView(repairPart = item, onClick = {
                        navController.navigate(Screens.TaskRepairParts.route + "/${taskId}/${item.id}")
                    })
                }
            }
            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp)
                )
            }

            if (state.value.error.isNotEmpty()) {
                Text(
                    text = state.value.error,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}