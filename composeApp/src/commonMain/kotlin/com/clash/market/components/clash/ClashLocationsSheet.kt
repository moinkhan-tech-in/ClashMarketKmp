package com.clash.market.components.clash

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            val scope = rememberCoroutineScope()
            ClashLocationsList(
                locations = locations,
                onItemClick = {
                    scope.launch {
                        onItemClick(it)
                        sheetState.hide()
                        onDismiss()
                    }
                }
            )
        }
    }
}