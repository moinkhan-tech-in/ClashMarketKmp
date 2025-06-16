package com.clash.market.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.ClashPicker
import com.clash.market.components.ClashToggleButton
import com.clash.market.models.WarFrequency


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchClanFilterOptionsSheet(
    show: Boolean,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    SearchOptionRow {
                        Text("Clan Location:", fontSize = 14.sp)
                        ClashGlossyButton(text = "Any", onClick = {})
                    }
                    SearchOptionRow {
                        var clanICanJoin by remember { mutableStateOf(false) }
                        Text("Only clans I can join:", fontSize = 14.sp)
                        ClashToggleButton(
                            checked = clanICanJoin,
                            onChange = { clanICanJoin = clanICanJoin.not() }
                        )
                    }

                    SearchOptionRow {
                        var clanICanJoin by remember { mutableStateOf(false) }
                        Text("Only family friendly clan:", fontSize = 14.sp)
                        ClashToggleButton(
                            checked = clanICanJoin,
                            onChange = { clanICanJoin = clanICanJoin.not() }
                        )
                    }

                    SearchOptionColumn {
                        Text("War Frequency:", fontSize = 14.sp)
                        ClashPicker(
                            WarFrequency.entries.toList(),
                            selectedIndex = 0,
                            onItemChange = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchOptionRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
private fun SearchOptionColumn(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        content = content
    )
}
