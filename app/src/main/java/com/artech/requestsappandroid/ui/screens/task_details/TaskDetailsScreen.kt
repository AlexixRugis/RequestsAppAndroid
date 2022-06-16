package com.artech.requestsappandroid.ui.screens.task_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artech.requestsappandroid.data.remote.models.Part
import com.artech.requestsappandroid.data.remote.models.PartRequest
import com.artech.requestsappandroid.data.remote.models.RepairTask
import com.artech.requestsappandroid.ui.components.RepairRequestView
import com.artech.requestsappandroid.ui.screens.login.models.LoginEvent
import com.artech.requestsappandroid.ui.screens.task_details.models.TaskDetailSubState
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
            modifier = Modifier
                .padding(10.dp)
        ) {
            when (state.value.subState) {
                TaskDetailSubState.INFO -> ShowInfo(
                    task = state.value.info!!,
                    onClickAddParts = { viewModel.obtainEvent(TaskDetailsViewEvent.AddParts) },
                    onClickCompleteTask = { viewModel.obtainEvent(TaskDetailsViewEvent.CompleteTask) }
                )
                TaskDetailSubState.SELECT_PARTS -> ShowRepairParts(
                    repairParts = state.value.repairParts,
                    onClick = { viewModel.obtainEvent(TaskDetailsViewEvent.SelectRepairPart(it)) }
                )
                TaskDetailSubState.ADD_PART -> ShowAddPart(
                    part = state.value.selectedPart!!,
                    isError = false,
                    amountValue = state.value.selectedPartAmount,
                    onAmountChanged = { viewModel.obtainEvent(TaskDetailsViewEvent.SelectedPartAmountChanged(it)) },
                    onSubmit = { viewModel.obtainEvent(TaskDetailsViewEvent.SubmitPartAdding) }
                )
            }
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

@Composable
fun ShowRepairParts(repairParts: List<Part>, onClick: (id: Int) -> Unit) {
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
            itemsIndexed(repairParts) { i, item ->
                RepairPartView(part = item, onClick = {
                    onClick(it)
                })
            }
        }
    }
}

@Composable
fun RepairPartView(part: Part, onClick: (id:Int) -> Unit) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(top = 10.dp)
            .clickable { onClick(part.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = part.name,
                modifier = Modifier.padding(end = 15.dp)
            )
            Text(
                text = "${part.price} руб.",
                modifier = Modifier.padding(end = 15.dp)
            )
        }
    }
}

@Composable
fun ShowAddPart(part: Part, isError: Boolean, amountValue: Int, onAmountChanged: (amount:Int) -> Unit, onSubmit: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        RepairPartView(part = part, onClick = {})
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = amountValue.toString(),
            onValueChange = {
                onAmountChanged(it.toIntOrNull()?:0)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Количество")
            },
            isError = isError
        )
        Spacer(modifier = Modifier.weight(1f, true))
        Button(
            onClick = {
                onSubmit()
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