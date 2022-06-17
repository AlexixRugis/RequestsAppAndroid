package com.artech.requestsappandroid.presentation.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artech.requestsappandroid.presentation.ui.screens.login.models.LoginEvent

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val loginState = viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("S")
            }
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("ign")
            }
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append(" I")
            }
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("n")
            }
        }, fontSize = 30.sp)
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = loginState.value.email,
            onValueChange = {
                viewModel.obtainEvent(LoginEvent.EmailChanged(it))
            },
            isError = loginState.value.emailErrorState,
            modifier = Modifier.fillMaxWidth(),
            enabled = loginState.value.isFormActive,
            label = {
                Text(text = "Enter Email*")
            }
        )
        if (loginState.value.emailErrorState) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = loginState.value.password,
            onValueChange = {
                viewModel.obtainEvent(LoginEvent.PasswordChanged(it))
            },
            isError = loginState.value.passwordErrorState,
            modifier = Modifier.fillMaxWidth(),
            enabled = loginState.value.isFormActive,
            label = {
                Text(text = "Enter Password*")
            },
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.obtainEvent(LoginEvent.ChangePasswordVisibility)
                }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "visibility",
                        tint = if (loginState.value.isPasswordHidden) Color.Gray else Color.Blue
                    )
                }
            },
            visualTransformation = if (loginState.value.isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (loginState.value.passwordErrorState) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = {
                viewModel.obtainEvent(LoginEvent.Login)
            },
            enabled = loginState.value.isFormActive,
            content = {
                Text(text = "Login", color = Color.White)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        )
    }
}