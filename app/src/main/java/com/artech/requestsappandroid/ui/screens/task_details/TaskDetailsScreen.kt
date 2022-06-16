package com.artech.requestsappandroid.ui.screens.task_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artech.requestsappandroid.data.remote.models.PartRequest
import com.artech.requestsappandroid.ui.components.RepairRequestView
import com.artech.requestsappandroid.ui.screens.task_details.models.TaskDetailsViewEvent

@Composable
fun TaskDetailsScreen(viewModel: TaskDetailsViewModel) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.obtainEvent(TaskDetailsViewEvent.EnterScreen)
    }

    if (state.value.isLoading) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Загрузка...",
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                RepairRequestView(request = state.value.info!!.request)
                Spacer(
                    modifier = Modifier.height(30.dp)
                )
                Text(
                    text = "Добавленные детали",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(state.value.info!!.parts) { i, item ->
                        RepairPartRequest(request = item)
                    }
                }
                OutlinedButton(
                    onClick = { viewModel.obtainEvent(TaskDetailsViewEvent.AddParts) }
                ) {
                    Text(
                        text = "Добавить детали",
                        fontSize = 16.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                Button(
                    onClick = {
                        viewModel.obtainEvent(TaskDetailsViewEvent.CompleteTask)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Завершить",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RepairPartRequest(request: PartRequest) {
    Card(
       elevation = 8.dp,
       modifier = Modifier.padding(top=10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = request.part.name,
                modifier = Modifier.padding(end = 15.dp)
            )
            Text(
                text = "${request.part.price} руб. * ${request.amount}",
                modifier = Modifier.padding(end = 15.dp)
            )
        }
    }
}