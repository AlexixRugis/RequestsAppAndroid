package com.artech.requestsappandroid.ui.screens.requests

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artech.requestsappandroid.data.remote.models.RepairRequest
import com.artech.requestsappandroid.ui.screens.requests.models.RequestsViewEvent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RequestsScreen(viewModel: RequestsViewModel) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.obtainEvent(RequestsViewEvent.EnterScreen)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.value.isLoading),
            onRefresh = {
                viewModel.obtainEvent(RequestsViewEvent.ReloadRequests)
            },
        ) {
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
                    itemsIndexed(state.value.requests) { i, item ->
                        RepairRequest(request = item, onClicked = {
                            viewModel.obtainEvent(RequestsViewEvent.ClickRequestItem(it))
                        })
                    }
                }
            }
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

