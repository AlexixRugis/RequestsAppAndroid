package com.artech.requestsappandroid.presentation.ui.screens.change_password

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ChangePasswordScreen(navController: NavController, viewModel: ChangePasswordViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.value.password,
                onValueChange = {
                    viewModel.setPassword(it)
                },
                isError = state.value.passwordError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Введите новый пароль*", style = MaterialTheme.typography.body1)
                },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = state.value.passwordConfirm,
                onValueChange = {
                    viewModel.setPasswordConfirm(it)
                },
                isError = state.value.passwordError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Подтвердите пароль*", style = MaterialTheme.typography.body1,)
                },
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.value.passwordError.isNotEmpty()) {
                Text(text = state.value.passwordError, color = MaterialTheme.colors.error, style = MaterialTheme.typography.body1,)
            }

            Spacer(modifier = Modifier.weight(1f, true))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.submit() },
                enabled = state.value.passwordError.isEmpty(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(
                    text = "Сохранить",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }

    if (state.value.isChanged) {
        navController.navigateUp()
    }
}