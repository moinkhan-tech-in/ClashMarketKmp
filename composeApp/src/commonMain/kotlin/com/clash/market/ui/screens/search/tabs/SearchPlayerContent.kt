package com.clash.market.ui.screens.search.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clash.market.components.ClashSearchTextField
import com.clash.market.ui.contents.playerdetail.PlayerDetailContent

@Composable
fun SearchPlayerContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.size(16.dp))
        var value by remember { mutableStateOf("") }
        ClashSearchTextField(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = value,
            onValueChange = { value = it.uppercase() },
            hint = "#PlayerTag",
        )
        PlayerDetailContent(value)
    }
}
