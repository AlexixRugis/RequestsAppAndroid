package com.artech.requestsappandroid.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AccountScreen() {
    Text(text = "account", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
}