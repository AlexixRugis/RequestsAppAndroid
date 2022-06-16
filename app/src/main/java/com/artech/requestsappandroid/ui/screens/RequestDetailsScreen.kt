package com.artech.requestsappandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artech.requestsappandroid.ui.components.RepairRequestView
import com.artech.requestsappandroid.ui.viewmodels.RequestDetailViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RequestDetailsScreen(viewModel: RequestDetailViewModel) {
    val mutableRequest = viewModel.mutableRepairRequest.value

    if (mutableRequest != null) {
        RepairRequestView(request = mutableRequest)
    } else {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Загрузка...",
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}