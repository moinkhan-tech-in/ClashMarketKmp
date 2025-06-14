package com.clash.market.components.clash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.components.ClashSearchTextField
import com.clash.market.models.Location
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClashLocationsSheet(
    show: Boolean,
    locations: List<Location>,
    onItemClick: (Location) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var searchValue by remember { mutableStateOf("") }
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Scaffold(
                topBar = {
                    ClashSearchTextField(
                        hint = "Search",
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                        value = searchValue,
                        onValueChange = { searchValue = it }
                    )
                }
            ) { innerPadding ->

                val scope = rememberCoroutineScope()
                val filteredLocations = locations
                    .filter { it.name.contains(searchValue, ignoreCase = true) }

                LazyColumn(
                    Modifier.padding(innerPadding),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
                ) {
                    items(filteredLocations) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        onItemClick(it)
                                        sheetState.hide()
                                        onDismiss()
                                    }
                                }
                                .padding(16.dp),
                            fontSize = 18.sp,
                            text = it.name
                        )
                    }
                }
            }
        }
    }
}