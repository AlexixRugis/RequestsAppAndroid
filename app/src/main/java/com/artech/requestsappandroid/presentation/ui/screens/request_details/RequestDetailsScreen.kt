package com.artech.requestsappandroid.presentation.ui.screens.request_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.artech.requestsappandroid.presentation.ui.components.RepairRequestView

@Composable
fun RequestDetailsScreen(navController: NavController, viewModel: RequestDetailsViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.request != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                RepairRequestView(request = state.value.request!!)
                Spacer(modifier = Modifier.weight(1f, true))
                Button(
                    onClick = {
                        viewModel.acceptRequest()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Принять",
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

        if (state.value.isAccepted) {
            navController.navigateUp()
        }
    }
}