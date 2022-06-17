package com.artech.requestsappandroid.presentation.ui.screens.requests

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
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
import com.artech.requestsappandroid.data.remote.dto.RepairRequest
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens

@Composable
fun RequestsScreen(navController: NavController, viewModel: RequestsViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.requests != null) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Доступные заявки",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 22.sp
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(state.value.requests!!) { i, item ->
                        RepairRequest(request = item, onClicked = {
                            navController.navigate(Screens.RequestDetails.route + "/${item.id}")
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

@Composable
fun RepairRequest(request: RepairRequest, onClicked: (id: Int) -> Unit ) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .clickable {
                onClicked(request.id)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column() {
                Text(
                    text = request.org_name,
                    fontSize = 20.sp
                )
                Text(
                    text = request.name,
                    fontSize = 16.sp
                )
            }
        }
    }
}

