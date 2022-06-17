package com.artech.requestsappandroid.presentation.ui.screens.task_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.artech.requestsappandroid.data.remote.dto.PartRequest
import com.artech.requestsappandroid.data.remote.dto.RepairTask
import com.artech.requestsappandroid.presentation.ui.components.RepairRequestView
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens

@Composable
fun TaskDetailsScreen(navController: NavController, taskId: Int, viewModel: TaskDetailsViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.task != null) {
            ShowInfo(
                task = state.value.task!!,
                onClickAddParts = { navController.navigate(Screens.TaskRepairParts.route + "/${taskId}") },
                onClickCompleteTask = { viewModel.completeTask() }
            )
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
fun ShowInfo(task: RepairTask, onClickAddParts: () -> Unit, onClickCompleteTask: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RepairRequestView(request = task.request)
            Spacer(
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "Добавленные запчасти",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .scrollEnabled(true)
                    .heightIn(0.dp, 160.dp)
            ) {
                itemsIndexed(task.parts) { i, item ->
                    RepairPartRequest(request = item)
                }
            }
            OutlinedButton(
                onClick = { onClickAddParts() }
            ) {
                Text(
                    text = "Добавить запчасти",
                    fontSize = 16.sp
                )
            }

            Spacer(
                modifier = Modifier.weight(1f, true)
            )

            Button(
                onClick = {
                    onClickCompleteTask()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 40.dp)
            ) {
                Text(
                    text = "Завершить",
                    fontSize = 16.sp
                )
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

fun Modifier.scrollEnabled(
    enabled: Boolean,
) = nestedScroll(
    connection = object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset = if(enabled) Offset.Zero else available
    }
)