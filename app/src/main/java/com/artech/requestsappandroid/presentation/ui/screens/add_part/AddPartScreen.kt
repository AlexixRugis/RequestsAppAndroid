package com.artech.requestsappandroid.presentation.ui.screens.add_part

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.artech.requestsappandroid.presentation.ui.components.RepairPartView

@Composable
fun AddPartScreen(navController: NavController, viewModel: AddPartViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.initialize()
    }

    Surface(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        color = MaterialTheme.colors.background
    ) {
        if (state.value.repairPart != null) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                RepairPartView(repairPart = state.value.repairPart!!, onClick = {})
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = state.value.amount.toString(),
                    onValueChange = {
                        viewModel.changeAmount(it.toIntOrNull()?:0)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Количество")
                    }
                )
                Spacer(modifier = Modifier.weight(1f, true))
                Button(
                    onClick = {
                        viewModel.addPart()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Добавить",
                        style = MaterialTheme.typography.button
                    )
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
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
        }

        if (state.value.isAdded) {
            navController.navigateUp()
        }
    }
}