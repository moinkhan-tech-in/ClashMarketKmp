package com.clash.market.ui.screens.search.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clash.market.components.ClashTextField

@Composable
fun SearchPlayerContent() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.size(16.dp))
        var value by remember { mutableStateOf("") }
        ClashTextField(
            value = value,
            hint = "#PlayerTag",
            onValueChange = { value = it.uppercase() }
        )
    }
}
