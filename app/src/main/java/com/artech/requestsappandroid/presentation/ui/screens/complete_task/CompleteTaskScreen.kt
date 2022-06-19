package com.artech.requestsappandroid.presentation.ui.screens.complete_task

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun CompleteTaskScreen(navController: NavController, viewModel: CompleteTaskViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            viewModel.submitImage(context, it)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Для завершения вам нужно прикрепить фотоотчёт",
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (state.value.error.isNotEmpty()) {
                Text(
                    text = state.value.error
                )
            }
            Spacer(modifier = Modifier.weight(1f, true))
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Выбрать фото"
                )
            }

            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }

    if (state.value.isCompleted) {
        navController.navigateUp()
    }
}