package com.artech.requestsappandroid.presentation.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens

@Composable
fun SettingsScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screens.ChangePassword.route) }
        ) {
            Text(
                text = "Смена пароля",
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}