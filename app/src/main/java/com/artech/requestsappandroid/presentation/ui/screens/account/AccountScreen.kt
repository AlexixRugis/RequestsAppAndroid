package com.artech.requestsappandroid.presentation.ui.screens.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.artech.requestsappandroid.data.remote.dto.Employee
import com.artech.requestsappandroid.data.remote.dto.RepairTask
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens

@Composable
fun AccountScreen(navController: NavController, viewModel: AccountViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.accountData != null) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AccountData(state.value.accountData!!.employee)
                Text(
                    text = "Принятые заявки",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 22.sp
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(state.value.accountData!!.tasks) { i, item ->
                        RepairTask(task = item, onClicked = {
                            navController.navigate(Screens.TaskDetails.route + "/${item.id}")
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
fun RepairTask(task: RepairTask, onClicked: (id: Int) -> Unit ) {
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
fun AccountData(employee: Employee) {
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
                    text = employee.name,
                    fontSize = 24.sp,
                    color = Color.White
                )
                Text(
                    text = employee.phone_number,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
                Text(
                    text = employee.post.name,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
        }
    }

}