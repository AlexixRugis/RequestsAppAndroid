package com.artech.requestsappandroid.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artech.requestsappandroid.data.remote.dto.RepairRequest


@Composable
fun RepairRequestView(request: RepairRequest) {
    Surface(
        modifier = Modifier.fillMaxWidth().background(
            MaterialTheme.colors.surface,
            MaterialTheme.shapes.small
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
        ) {
            Text(
                text = "Клиент",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h5,
            )
            Text(
                text = request.org_name,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                text = request.org_address,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                text = request.org_phone,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))
            Text(
                text = "Информация о проблеме",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h5,
            )
            Text(
                text = request.name,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = request.description,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                text = request.desired_date,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }
}