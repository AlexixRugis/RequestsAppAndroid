package com.artech.requestsappandroid.presentation.ui.screens.complete_task

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
            .padding(10.dp),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Для завершения вам нужно прикрепить фотоотчёт",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (state.value.error.isNotEmpty()) {
                Text(
                    text = state.value.error,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1,
                )
            }
            Spacer(modifier = Modifier.weight(1f, true))
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(
                    text = "Выбрать фото",
                    style = MaterialTheme.typography.body1,
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