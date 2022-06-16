package com.artech.requestsappandroid.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artech.requestsappandroid.data.remote.models.RepairRequest


@Composable
fun RepairRequestView(request: RepairRequest) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Клиент",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = request.org_name,
                fontSize = 22.sp
            )
            Text(
                text = request.org_address,
                fontSize = 16.sp
            )
            Text(
                text = request.org_phone,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))
            Text(
                text = "Информация о проблеме",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = request.name,
                fontSize = 22.sp
            )
            Text(
                text = request.description,
                fontSize = 16.sp
            )
            Text(
                text = request.desired_date,
                fontSize = 16.sp
            )
        }
    }
}