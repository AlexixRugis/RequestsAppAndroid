package com.artech.requestsappandroid.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchView(onClickSearch: (searchQuery: String) -> Unit) {
    val search = remember {
        mutableStateOf("")
    }

    Row(
       modifier = Modifier
           .fillMaxWidth()
           .height(40.dp)
    ) {
        CustomTextField(
            value = search.value,
            onValueChange = {search.value = it},
            fontSize = 16.sp,
            placeholderText = "Найти",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(5.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f, true))
        Button(onClick = { onClickSearch(search.value) }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        }
    }
}