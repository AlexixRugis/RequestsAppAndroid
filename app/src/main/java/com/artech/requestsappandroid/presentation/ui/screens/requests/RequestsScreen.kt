package com.artech.requestsappandroid.presentation.ui.screens.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.artech.requestsappandroid.data.remote.dto.RepairRequest
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens

@Composable
fun RequestsScreen(navController: NavController, viewModel: RequestsViewModel = hiltViewModel()) {
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
                text = "Доступные заявки",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(10.dp),
                fontSize = 22.sp
            )
            if (state.value.data != null) {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(state.value.data ?: emptyList()) { i, item ->
                        RepairRequest(request = item, onClicked = {
                            navController.navigate(Screens.RequestDetails.route + "/${item.id}")
                        })
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
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun RepairRequest(request: RepairRequest, onClicked: (id: Int) -> Unit ) {
    Card(
        elevation = 1.dp,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clickable {
                onClicked(request.id)
            }.background(
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column() {
                Text(
                    text = request.org_name,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = request.name,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

