package com.artech.requestsappandroid.ui.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.artech.requestsappandroid.MainViewModel
import com.artech.requestsappandroid.navigation.Screens
import kotlinx.coroutines.delay
import okhttp3.internal.wait

@Composable
fun SplashScreen(navController: NavController, viewModel: MainViewModel) {
    var startAnimate by remember {
        mutableStateOf(false)
    }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimate) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )

    LaunchedEffect(key1 = true) {
        startAnimate = true
        delay(3000)

        viewModel.getAuthenticationStatus()?.let {
            if (it.is_authenticated)
                navController.navigate(Screens.Account.route)
            else
                navController.navigate(Screens.Login.route)
        }

        startAnimate = false
        delay(1500)
    }


    Splash(alphaAnimation.value)
}

@Composable
fun Splash(animationState: Float) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .scale(animationState)
                .alpha(animationState),
            imageVector = Icons.Default.Person,
            contentDescription = "",
            tint = Color.Black
        )
    }
}