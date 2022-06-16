package com.artech.requestsappandroid.ui.screens.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artech.requestsappandroid.data.remote.models.RepairTask
import com.artech.requestsappandroid.ui.screens.account.models.AccountViewEvent
import com.artech.requestsappandroid.ui.screens.account.models.AccountViewState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun AccountScreen(viewModel: AccountViewModel) {
    val accountInfo = viewModel.accountInfo.collectAsState()
    val tasksInfo = viewModel.tasksInfo.collectAsState()

    val isExiting = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.obtainEvent(AccountViewEvent.EnterScreen)
    }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(tasksInfo.value.isLoading),
            onRefresh = {
                viewModel.obtainEvent(AccountViewEvent.RefreshTasksData)
            },
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AccountData(accountInfo.value)
                Text(
                    text = "Принятые заявки",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 22.sp
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(tasksInfo.value.tasks) { i, item ->
                        RepairRequest(task = item, onClicked = {
                            viewModel.obtainEvent(AccountViewEvent.RepairRequestClicked(it))
                        })
                    }
                }    
            }
            
        }
    }
}

@Composable
fun RepairRequest(task: RepairTask, onClicked: (id: Int) -> Unit ) {
    Card(
       elevation = 4.dp,
       modifier = Modifier
           .padding(top = 8.dp)
           .fillMaxWidth()
           .clickable {
               onClicked(task.id)
           }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column() {
                Text(
                    text = task.request.org_name,
                    fontSize = 20.sp
                )
                Text(
                    text = task.request.name,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun AccountData(state: AccountViewState) {
    Surface(
        color = MaterialTheme.colors.primarySurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Column() {
                Text(
                    text = if (!state.isLoading) state.info.name else "Загрузка...",
                    fontSize = 24.sp,
                    color = Color.White
                )
                Text(
                    text = if (!state.isLoading) state.info.phone_number else "Загрузка...",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
                Text(
                    text = if (!state.isLoading) state.info.post.name else "Загрузка...",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
        }
    }

}