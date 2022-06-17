package com.artech.requestsappandroid.presentation.ui.screens.add_part

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.part != null) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                RepairPartView(part = state.value.part!!, onClick = {})
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Добавить",
                        fontSize = 16.sp
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
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
        }

        if (state.value.isAdded) {
            navController.navigateUp()
        }
    }
}