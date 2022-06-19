package com.artech.requestsappandroid.presentation.ui.screens.select_parts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
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
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens

@Composable
fun SelectPartsScreen(navController: NavController, taskId: Int, viewModel: SelectPartsViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.initialize()
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.data != null) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Запчасти",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(state.value.data?: emptyList()) { i, item ->
                        RepairPartView(repairPart = item, onClick = {
                            navController.navigate(Screens.TaskRepairParts.route + "/${taskId}/${item.id}")
                        })
                    }
                }
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
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}