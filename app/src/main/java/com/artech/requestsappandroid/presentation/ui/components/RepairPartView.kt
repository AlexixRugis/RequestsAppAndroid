package com.artech.requestsappandroid.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artech.requestsappandroid.data.remote.dto.Part

@Composable
fun RepairPartView(part: Part, onClick: () -> Unit) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Text(
                text = part.name,
            )
            Text(
                text = "${part.price} руб.",
            )
        }
    }
}