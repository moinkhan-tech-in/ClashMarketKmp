package com.clash.market.components.clash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.components.ClashSearchTextField
import com.clash.market.models.Location

@Composable
fun ClashLocationsList(
    locations: List<Location>,
    onItemClick: (Location) -> Unit,
    leadingTopBarContent: @Composable () -> Unit = {},
    trailingTopBarContent: @Composable () -> Unit = {},
) {
    var searchValue by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            Row(
                Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leadingTopBarContent()
                ClashSearchTextField(
                    hint = "Search",
                    modifier = Modifier.weight(1f),
                    value = searchValue,
                    onValueChange = { searchValue = it }
                )
                trailingTopBarContent()
            }

        }
    ) { innerPadding ->

        val filteredLocations = locations
            .filter { it.name.contains(searchValue, ignoreCase = true) }

        LazyColumn(
            Modifier.padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
        ) {
            items(filteredLocations, key = { it.id ?: it.name }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(it) }
                        .padding(16.dp),
                    fontSize = 18.sp,
                    text = it.name
                )
            }
        }
    }
}