package com.artech.requestsappandroid.presentation.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.artech.requestsappandroid.presentation.ui.screens.main.MainViewModel
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens
import com.artech.requestsappandroid.presentation.ui.screens.main.models.MainViewEvent
import com.artech.requestsappandroid.utils.Constants
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference

@Composable
fun SettingsScreen(navController: NavController, viewModel: MainViewModel) {
    val state = viewModel.state.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(Screens.ChangePassword.route) },
                backgroundColor = MaterialTheme.colors.surface,
            ) {
                Text(
                    text = "Смена пароля",
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Тема",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.weight(1f, true))
                Icon(
                    imageVector = if (state.value.darkTheme) Icons.Default.Star else Icons.Default.Add,
                    contentDescription = "theme_button",
                    Modifier.clickable { viewModel.obtainEvent(MainViewEvent.ToggleDarkTheme) }
                )
            }
        }
    }
}