package com.artech.requestsappandroid.ui.request_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artech.requestsappandroid.ui.components.RepairRequestView
import com.artech.requestsappandroid.ui.request_details.models.RequestDetailsEvent

@Composable
fun RequestDetailsScreen(viewModel: RequestDetailsViewModel) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.obtainEvent(RequestDetailsEvent.EnterScreen)
    }

    if (!state.value.isLoading) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        ) {
            RepairRequestView(request = state.value.info!!)
            Spacer(modifier = Modifier.weight(1f, true))
            Button(
                onClick = {
                    viewModel.obtainEvent(RequestDetailsEvent.AcceptRequest)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Принять",
                    fontSize = 16.sp
                )
            }
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Загрузка...",
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}